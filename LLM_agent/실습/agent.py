# base
import os
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI

# agent
from langchain.agents import create_openai_tools_agent, AgentExecutor
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain_core.runnables import RunnablePassthrough, RunnableLambda

# memory
from langchain.memory import ConversationBufferMemory

# web search
from langchain_tavily import TavilySearch

# RAG
from langchain_community.document_loaders import DirectoryLoader, TextLoader
from langchain_text_splitters import MarkdownTextSplitter
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma
from langchain.tools.retriever import create_retriever_tool

# 출력
from langchain.callbacks.streaming_stdout import StreamingStdOutCallbackHandler

import warnings

warnings.filterwarnings('ignore', category=DeprecationWarning)
load_dotenv()

# LLM
llm = ChatOpenAI(
    model='gpt-4.1-nano', 
    temperature=0,
    streaming=True,
    callbacks=[StreamingStdOutCallbackHandler()]
)

# 프롬프트
system_message = f'''
너는 웹 검색도 가능하고, 사용자가 공부한 내용도 검색할 수 있는 어시스턴트야.

사용자는 AI 기반 데이터 분석가 양성 과정에서 SQL, 파이썬, 머신러닝, LLM 등에 대해 공부했어.

rag_search 툴 안에는 사용자가 공부한 내용이 들어있어.

사용자가 질문한 내용이 공부한 내용과 관련이 있다면, 먼저 rag_search 툴을 사용해서 답변을 생성해.

공부한 내용과 관련이 있는데, rag_search로 관련도가 높은 정보를 찾지 못했다면, web_search를 사용해 답변을 생성해도 돼.

만약 질문 내용이 공부한 내용과 관련이 없다면, web_search 툴을 사용해 관련 내용을 검색하고, 답변을 생성해.

만약 web_search를 이용했다면, 웹 검색을 진행했다는 내용을 답변에 포함해야 해. 또, 어디서 검색했는지 출처를 마지막에 추가해 줘.

질문 내용을 이해하지 못했다면, 그냥 질문을 이해하지 못했다고 말해.

만약 답을 모르겠다면, 그냥 답을 모르겠다고 말해.

가장 의미있는 결과들을 정리해서 질문에 답해줘.

'''

prompt = ChatPromptTemplate([
    ('system', system_message),
    MessagesPlaceholder(variable_name='chat_history'),
    ('human', '{input}'),
    MessagesPlaceholder(variable_name='agent_scratchpad')
])

# 메모리
memory = ConversationBufferMemory(
    return_messages=True,
    memory_key='chat_history'
)

# 웹서치
web_search = TavilySearch(
    max_results=5,
    topic='general',
    search_depth='advanced'
)

# 사전처리
folder_path = '../'
vectorstore_path = './vectorstore'

# 벡터스토어가 있으면 그대로 가져옴
if os.path.exists(vectorstore_path) and os.listdir(vectorstore_path):
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=vectorstore_path, embedding_function=embedding)
    
# 없을경우 생성
else:
    loader = DirectoryLoader(
        '../../',
        glob='**/*.md',
        loader_cls=lambda path: TextLoader(path, encoding='utf-8'),
        show_progress=False
    )
    documents = loader.load()

    text_splitter = MarkdownTextSplitter(
        chunk_size=1000,
        chunk_overlap=200
    )
    split_docs = text_splitter.split_documents(documents)

    embedding = OpenAIEmbeddings()
    vectorstore = Chroma.from_documents(documents=split_docs, embedding=embedding, persist_directory=vectorstore_path)
    vectorstore.persist()

# RAG
retriever = vectorstore.as_retriever()

rag_tool = create_retriever_tool(
    retriever,
    name='md_search',
    description='수업자료에서 관련된 내용을 검색합니다'
)

# Tools 설정
tools = [web_search, rag_tool]

agent = create_openai_tools_agent(
    llm=llm,
    tools=tools,
    prompt=prompt
)

agent_executor = AgentExecutor(
    agent=agent,
    tools=tools,
    memory=memory,
    verbose=False
)

if __name__ == '__main__':
    print('챗봇 시작. 도움말은 @help')
    
    while True:
        user_input = input('\n입력:\n')
        if user_input == '@quit':
            print('\n챗봇을 종료합니다')
            break
        elif user_input == '@clear':
            memory.clear()
            print('\n챗봇의 메모리를 삭제합니다')
        elif user_input == '@help':
            print('\n@quit: 종료\n@clear: 메모리 초기화')
        else:
            print('\n답변:')
            result = agent_executor.invoke({'input': user_input})
