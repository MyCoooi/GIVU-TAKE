from pydantic import BaseSettings

class Settings(BaseSettings):
    PROJECT_NAME: str = "Location Recommender API"
    SECRET_KEY: str = "your-secret-key"  # 실제 운영 환경에서는 안전한 값으로 변경해야 합니다
    ALGORITHM: str = "HS256"
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 30

    class Config:
        env_file = ".env"

settings = Settings()