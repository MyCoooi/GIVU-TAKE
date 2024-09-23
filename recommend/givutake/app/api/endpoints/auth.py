from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordRequestForm
from app.core.security import create_access_token
from datetime import timedelta
from app.core.config import settings

router = APIRouter()

@router.post("/token")
async def login_for_access_token(form_data: OAuth2PasswordRequestForm = Depends()):
    # 여기서 사용자 인증을 수행해야 합니다.
    # 이 예제에서는 간단히 이메일을 토큰에 포함시킵니다.
    # 실제 애플리케이션에서는 비밀번호 확인 등의 추가 인증 단계가 필요합니다.
    user_email = form_data.username  # OAuth2PasswordRequestForm에서는 username 필드를 이메일로 사용
    
    access_token_expires = timedelta(minutes=settings.ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = create_access_token(
        data={"sub": user_email}, expires_delta=access_token_expires
    )
    return {"access_token": access_token, "token_type": "bearer"}