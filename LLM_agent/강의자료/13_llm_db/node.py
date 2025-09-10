from classes import State, QueryAnalysis, QueryOutput
from db import db, table_info
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate
from langchain_community.tools.sql_database.tool import QuerySQLDatabaseTool
from dotenv import load_dotenv

load_dotenv()

llm = ChatOpenAI(model='gpt-4.1', temperature=0)

system_message = """
Given an input question, create a syntactically correct {dialect} query to
run to help find the answer. Unless the user specifies in his question a
specific number of examples they wish to obtain, always limit your query to
at most {top_k} results. You can order the results by a relevant column to
return the most interesting examples in the database.

Never query for all the columns from a specific table, only ask for a the
few relevant columns given the question.

Pay attention to use only the column names that you can see in the schema
description. Be careful to not query for columns that do not exist. Also,
pay attention to which column is in which table.

Only use the following tables:
{table_info}
"""

user_prompt = 'Question: {input}'

query_prompt_template = ChatPromptTemplate(
    {
        ('system', system_message),
        ('user', user_prompt),
    }
)

def query_analysis(state: State):
    '''사용자의 질문이 주어진 데이터베이스를 통해 해결할 수 있는지 판단'''
    prompt = f'''
    사용자의 질문이 주어진 데이터베이스에 들어있는 데이터를 이용해 해결할 수 있는지 분석하고, 그 결과를 Yes, No로 답변해.
    ---
    Question: {state['question']}
    Table Information: {table_info}
    '''
    structured_llm = llm.with_structured_output(QueryAnalysis)
    result = structured_llm.invoke(prompt)
    return {'analysis': result['analysis']}

def write_sql(state: State):
    '''Generate SQL query to fetch info'''
    prompt = query_prompt_template.invoke({
        'dialect': db.dialect,
        'top_k': 10,
        'table_info': db.get_table_info(),
        'input': state['question']
    })
    structured_llm = llm.with_structured_output(QueryOutput)
    result = structured_llm.invoke(prompt)
    return {'sql': result['query']}


def execute_sql(state: State):
    '''Execute SQL Query'''
    execute_sql_tool = QuerySQLDatabaseTool(db=db)
    result = execute_sql_tool.invoke(state['sql'])
    return {'result': result}

def generate_normal(state: State):
    '''Answer question that does not fit database'''
    prompt = f'''
    주어진 사용자 질문에 대해, 질문이 현재 데이터베이스를 통해서는 구할 수 없다는 것을 언급하고,
    왜 그러지 못하는지 이유까지 간단하게 설명해.
    ---
    
    Question: {state['question']}
    Table Information: {table_info}    
    '''
    res = llm.invoke(prompt)
    return {'answer': res.content}

def generate_answer(state: State):
    '''Generate answer using retrieved information as context'''
    prompt = f'''
    주어진 사용자 질문에 대해, DB에서 실행된 SQL 쿼리와 그 결과를 바탕으로 답변해.
    
    Question: {state['question']}
    ---
    SQL query: {state['sql']}
    SQL result: {state['result']}
    '''
    res = llm.invoke(prompt)
    return {'answer': res.content}