from langgraph.graph import START, END, StateGraph
from classes import State
from node import query_analysis, write_sql, execute_sql, generate_answer, generate_normal
from router import analysis_decide

builder = StateGraph(State)

builder.add_node('query_analysis', query_analysis)
builder.add_node('write_sql', write_sql)
builder.add_node('execute_sql', execute_sql)
builder.add_node('generate_answer', generate_answer)
builder.add_node('generate_normal',generate_normal)

builder.add_edge(START, 'query_analysis')

builder.add_conditional_edges(
    'query_analysis',
    analysis_decide,
    {
        'write_sql': 'write_sql',
        'generate_normal': 'generate_normal'
    }
)

builder.add_edge('write_sql', 'execute_sql')
builder.add_edge('execute_sql', 'generate_answer')
builder.add_edge('generate_answer', END)
builder.add_edge('generate_normal', END)

graph = builder.compile()