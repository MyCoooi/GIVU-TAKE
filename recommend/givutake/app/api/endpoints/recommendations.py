from fastapi import APIRouter, Depends, HTTPException, status
from app.core.security import get_current_user, decode_email
from app.schemas.location import LocationResponse
from app.services.recommendation_service import get_recommendations

router = APIRouter()

@router.get("/locations/for/{encoded_email}", response_model=LocationResponse)
async def recommend_locations(encoded_email: str, current_user: str = Depends(get_current_user)):
    email = decode_email(encoded_email)
    
    if current_user != email:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You don't have permission to access this resource",
        )
    
    recommended_locations = await get_recommendations(email)
    return LocationResponse(locations=recommended_locations)