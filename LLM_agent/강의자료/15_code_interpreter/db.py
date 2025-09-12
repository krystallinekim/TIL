from langchain_community.utilities import SQLDatabase
import os

POSTGRES_URI = os.getenv('POSTGRES_URI')

db = SQLDatabase.from_uri(
    POSTGRES_URI,
    include_tables=['body_measurements']
)

data = db.run('SELECT * FROM body_measurements')