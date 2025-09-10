from graph.builder import graph

if __name__ == '__main__':
    answer = graph.invoke({'question': input('질문을 입력하세요:\n')})
    print(answer['answer'])