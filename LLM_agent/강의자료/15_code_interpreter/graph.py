from langgraph.graph import StateGraph, START, END
from state import State
from nodes import generate_code, execute_code, save_code, generate_answer

builder = StateGraph(State)
builder.add_sequence([generate_code, execute_code, save_code, generate_answer])

builder.add_edge(START, 'generate_code')
builder.add_edge('generate_answer', END)

graph = builder.compile()