from content_model.models import Genre
from content_model.models import User
from rest_framework import serializers



class GenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        fields = '__all__'
    

class UpdateGenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        # fields = ('userid', 'name', 'description', 'updated_at')
        exclude=('created_at',)

        
