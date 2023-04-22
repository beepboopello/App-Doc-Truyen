from rest_framework import serializers
from .models import User, Subscription, PaidSubscription


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        # fields = ('id','username','password','admin','email')
        fields = '__all__'


