from content_model.models import PaidSubcription
from content_model.models import Subcription
from rest_framework import serializers

class SubcriptionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Subcription
        fields = '__all__'
        
class PaidSubcriptionSerializer(serializers.ModelSerializer):
    
    subcriptionId = SubcriptionSerializer(read_only=True, many=True)
    
    class Meta:
        model = PaidSubcription
        fields = '__all__'
    # def create(self, validated_data):
    #     liked = Liked.objects.create(
    #         userid = int(validated_data['userid']),
    #         titleId = validated_data['titleId']
    #     )
    #     liked.save()
    #     return liked