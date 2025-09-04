# 학습 어시스턴트 에이전트 작성
import os

# agent
from langchain_openai import ChatOpenAI
from langchain.agents import create_openai_tools_agent, AgentExecutor
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder

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

# 에러
import warnings

warnings.filterwarnings('ignore', category=DeprecationWarning)

# env
from dotenv import load_dotenv

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

당신은 웹 검색도 가능하고, 사용자가 공부한 내용도 검색할 수 있는 어시스턴트입니다.

사용자는 AI 기반 데이터 분석가 양성 과정에서 SQL, 파이썬, 머신러닝, LLM 등에 대해 공부했습니다.

다음 기준에 맞게 답변을 해주세요.

---

기준:

1. 사용자가 질문한 내용이 공부한 내용과 관련이 있다면, 먼저 rag_search 툴을 사용해서 답변을 생성합니다.

2. rag_search 결과가 관련도가 낮으면 해당 결과를 사용하지 않습니다. 관련도가 낮으면 무시하고 다음 단계로 진행합니다.

3. 관련도가 낮은 rag_search 결과만 있거나 관련 내용이 전혀 없으면, web_search를 사용해서 답변을 생성할 수 있습니다.

4. 질문 내용이 공부한 내용과 관련이 없다면, web_search를 사용해서 관련 내용을 검색하고 답변을 생성합니다.

5. web_search를 이용한 경우, 웹 검색을 진행했다는 사실과 출처를 답변 마지막에 명시합니다.

6. 질문 내용을 이해하지 못했으면, 이해하지 못했다고 솔직하게 말합니다.

7. 답을 모르면, 그냥 모른다고 답합니다.

---

항상 가장 의미 있는 결과를 정리해서 요점만 한국어로 전달하고, 항상 존댓말을 사용하세요.

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

def make_vectorstore():
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
    
    return vectorstore

# 벡터스토어가 있으면 그대로 가져옴
if os.path.exists(vectorstore_path) and os.listdir(vectorstore_path):
    embedding = OpenAIEmbeddings()
    vectorstore = Chroma(persist_directory=vectorstore_path, embedding_function=embedding)
    
# 없을경우 생성
else:
    vectorstore = make_vectorstore()
    
# RAG
retriever = vectorstore.as_retriever()

rag_tool = create_retriever_tool(
    retriever,
    name='md_search',
    description='수업자료에서 관련된 내용을 검색합니다'
)

# Tools 설정
tools = [web_search, rag_tool]

# agent 실행
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

# 챗봇 실행
if __name__ == '__main__':
    print('=== 챗봇을 시작합니다 === (도움말: @help)')
    
    while True:
        user_input = input('\n입력:\n')
        
        if not user_input:
            print('\n입력이 비어 있습니다. 다시 입력해 주세요.')
            continue        
        
        # 명령어
        elif user_input[:1] == '@':
            
            if user_input in ['@quit', '@q']:
                print('\n챗봇을 종료합니다')
                break
            
            elif user_input in ['@clear', '@clr']:
                memory.clear()
                print('\n챗봇의 메모리를 삭제합니다')
            
            elif user_input == '@history':
                chat_history = memory.load_memory_variables({}).get('chat_history', [])
                if not chat_history:
                    print('\n대화 기록이 없습니다')
                else:
                    print('\n=== 대화 기록 ===')
                    for i, msg in enumerate(chat_history, 1):
                        role = msg.type
                        print(f'{role}: {msg.content}\n')
            
            elif user_input == '@update':
                vectorstore = make_vectorstore()
                print('\n자료를 업데이트합니다.')
                
            elif user_input == '@help':
                print('\n=== 도움말 목록 ===')
                print('- @quit, @q: 종료\n- @clear, @clr: 메모리 초기화\n- @history: 대화 기록\n- @update: 자료 업데이트')
            
            else:
                print('\n인식할 수 없는 명령어입니다. @help를 입력해 도움말을 불러오세요.')    
                
        else:
            try:
                print('\n답변:')
                result = agent_executor.invoke({'input': user_input})
                print('\n')
                
            except Exception as e:
                print(f'오류 발생: {e}')
                continue
