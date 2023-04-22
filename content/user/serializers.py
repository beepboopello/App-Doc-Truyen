from rest_framework import serializers
from content_model.models import User, Subcription, PaidSubcription


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        # fields = ('id','username','password','admin','email')
        fields = '__all__'


