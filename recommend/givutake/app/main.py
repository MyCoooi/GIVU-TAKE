from fastapi import FastAPI
from app.api.endpoints import recommendations
from app.core.config import settings

app = FastAPI(title=settings.PROJECT_NAME)

app.include_router(recommendations.router, prefix="/recommend/v1")

if __name__ == "__main__":
    import uvicorn
    uvicorn.run("app.main:app", host="0.0.0.0", port=8000, reload=True)