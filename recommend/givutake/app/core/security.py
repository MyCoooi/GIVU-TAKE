from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2AuthorizationCodeBearer
import jwt
from app.core.config import settings
import base64

oauth2_scheme = OAuth2AuthorizationCodeBearer(authorizationUrl="token", tokenUrl="token")

def get_current_user(token: str = Depends(oauth2_scheme)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        # print(token)
        # print(settings.JWT_SECRET_ALGORITHM)
        # print(settings.JWT_SECRET_KEY)
        payload = jwt.decode(token, base64.b64decode(settings.JWT_SECRET_KEY).decode('utf-8'), algorithms=[settings.JWT_SECRET_ALGORITHM])
        email: str = payload.get("sub")
        if email is None:
            raise credentials_exception
    except jwt.PyJWTError:
        raise credentials_exception
    return email