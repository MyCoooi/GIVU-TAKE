from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
import jwt
from config import settings
import base64

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

def get_current_user(token: str):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, settings.JWT_SECRET_KEY, algorithms=[settings.JWT_SECRET_ALGORITHM])
        email: str = payload.get("sub")
        if email is None:
            raise credentials_exception
    except jwt.PyJWTError:
        raise credentials_exception
    return email

def create_access_token(data: dict):
    to_encode = data.copy()
    encoded_jwt = jwt.encode(to_encode, settings.JWT_SECRET_KEY, algorithm=settings.JWT_SECRET_ALGORITHM)
    return encoded_jwt

get_current_user('eyJhbGciOiJIUzUxMiJ9.eyJhdXRoIjoiUk9MRV9DT1JQT1JBVElPTiIsInN1YiI6InNzYWZ5QGV4YW1wbGUuY29tIiwiaXNzIjoiY29tLmFjY2VwdGVkLmdpdnV0YWtlIiwibmJmIjoxNzI3MDU2NzM5LCJpYXQiOjE3MjcwNTY3MzksImV4cCI6MTcyNzA1NzYzOSwianRpIjoiM2YwZjdiMWUtMTlkMS00YTJjLWIzNDItNjA4MDBjYmU0MzA0In0.NgaCeYrDMogqwVNrWneh4zIVkFJxv6Z-1Qf-2wZJG18i12l0MjD8g4I6hOObNAMKhd1wBI3ZmpEA-0EtnaUS1A')