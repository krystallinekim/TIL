from core.classes import State

def analysis_decide(state: State):
    """분석 결과에 따라 다음 단계를 결정"""
    if state.get('analysis') == 'Yes':
        return 'write_sql'
    else:
        return 'generate_normal'