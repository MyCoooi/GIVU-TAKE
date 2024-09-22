from pydantic import BaseSettings
from pathlib import Path
from dotenv import load_dotenv
import os

is_ec2 = os.getenv('IS_EC2', 'false').lower() == 'true'

if is_ec2:
    env_path = Path('/home/ubuntu/build/recommendation/') / '.env'
else:
    env_path = Path.cwd().parent / '.env'

load_dotenv(dotenv_path=env_path)

class Settings(BaseSettings):
    PROJECT_NAME: str = "Location Recommender API"
    SECRET_KEY: str = os.getenv("JWT_SECRET_KEY")
    ALGORITHM: str = os.getenv("JWT_ALGORITHM")
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 30

    class Config:
        env_file = ".env"

settings = Settings()