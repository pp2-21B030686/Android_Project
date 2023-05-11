from django.urls import path, include
from api.views import *

from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

urlpatterns = [
    path('products/', list_of_products),
    path('product/<int:id>/commentaries/', comments_by_product),
    path('product/<int:id>/', product_by_id),

    path('categories/', list_of_categories),
    path('category/<int:id>/products/', productsByCategory),

    path('cart/<int:id>/', list_of_orders_by_user),
    path('cart/<int:userId>/<int:productId>/', delete_product_from_order),

    path('register/user/', UserCreateView.as_view()),
    path('token/', TokenObtainPairView.as_view(), name='token_obtain_pair'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),

    path('user/<str:username>/', find_user_by_username),
    path('user/email/<str:username>', find_email_by_username),

    path('rating/<int:productId>/', product_ratings, name='ratings_for_a_product'),
    path('rating/<int:productId>/<int:userId>/', change_rating_for_user, name='get_user_rating_for_a_product')
]
