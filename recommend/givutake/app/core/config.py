from pydantic_settings import BaseSettings

class Settings(BaseSettings):
    PROJECT_NAME: str = "Location Recommender API"
    JWT_SECRET_ALGORITHM: str
    JWT_SECRET_KEY: str
    JWT_ACCESS_TOKEN_EXPIRATION_TIME: int
    JWT_REFRESH_TOKEN_EXPIRATION_TIME: int

    class Config:
        env_file = ".env"

settings = Settings()