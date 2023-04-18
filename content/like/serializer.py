from content_model.models import Liked
from content_model.models import Title
from rest_framework import serializers

class TitleSerializer(serializers.ModelSerializer):
    class Meta:
        model = Title
        fields = '__all__'
        
class LikedSerializer(serializers.ModelSerializer):
    
    titleId = TitleSerializer(read_only=True, many=True)
    
    class Meta:
        model = Liked
        fields = '__all__'
    # def create(self, validated_data):
    #     liked = Liked.objects.create(
    #         userid = int(validated_data['userid']),
    #         titleId = validated_data['titleId']
    #     )
    #     liked.save()
    #     return liked