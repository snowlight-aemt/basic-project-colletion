from django.contrib.auth.models import AbstractUser
from django.db import models


class User(AbstractUser):
    """User Models"""

    GENDER_MALE = "male"
    GENDER_FEMALE = "female"
    GENDER_OTHER = "other"

    # LEARN Models 에서 Tuple 활용 방법 (`DB 저장 값`, `필드 위젯 표시 값`)
    GENDER_CHOICES = (
        (GENDER_MALE, "Male"),
        (GENDER_FEMALE, "Female"),
        (GENDER_OTHER, "Other"),
    )

    LANGUAGE_ENGLISH = "en"
    LANGUAGE_KOREAN = "kr"

    LANGUAGE_CHOICES = (
        (LANGUAGE_ENGLISH, "English"),
        (LANGUAGE_KOREAN, "Korean"),
    )

    CURRENCY_USR = "usd"
    CURRENCY_KRW = "KRW"

    CURRENCY_CHOICES = (
        (CURRENCY_USR, "USD"),
        (CURRENCY_KRW, "KRW"),
    )

    avatar = models.ImageField(null=True, blank=True)
    gender = models.CharField(
        choices=GENDER_CHOICES, max_length=10, null=True, blank=True
    )
    bio = models.TextField(default="")
    birthdate = models.DateField(null=True)
    language = models.CharField(
        choices=LANGUAGE_CHOICES, max_length=2, null=True, blank=True
    )
    currency = models.CharField(
        choices=CURRENCY_CHOICES, max_length=3, null=True, blank=True
    )
    superhost = models.BooleanField(default=False)
