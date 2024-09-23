from pydantic_settings import BaseSettings
from pathlib import Path
from dotenv import load_dotenv
import os

is_ec2 = os.getenv('IS_EC2', 'false').lower() == 'true'

if is_ec2:
    env_path = Path('/home/ubuntu/build/recommendation/') / '.env'
else:
    env_path = Path.cwd() / 'app' / '.env'

load_dotenv(dotenv_path=env_path)

class Settings(BaseSettings):
    PROJECT_NAME: str = "Location Recommender API"
    JWT_SECRET_ALGORITHM: str
    JWT_SECRET_KEY: str
    JWT_ACCESS_TOKEN_EXPIRATION_TIME: int
    JWT_REFRESH_TOKEN_EXPIRATION_TIME: int

    class Config:
        env_file = ".env"

settings = Settings()