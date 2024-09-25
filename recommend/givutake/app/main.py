from fastapi import FastAPI
from app.api.endpoints import recommendations
from app.core.config import settings
from dotenv import load_dotenv
from pathlib import Path
import os

app = FastAPI(title=settings.PROJECT_NAME)

app.include_router(recommendations.router, prefix="/recommend")

is_ec2 = os.getenv('IS_EC2', 'false').lower() == 'true'

if is_ec2:
    env_path = Path('/home/ubuntu/build/recommendation/') / '.env'
else:
    env_path = Path.cwd() / '.env'
    print(env_path)

load_dotenv(dotenv_path=env_path, override=True)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app.main:app", host="0.0.0.0", port=8000, reload=True)