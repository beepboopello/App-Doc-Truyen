from content_model.models import Genre, Title
from content_model.models import User, GenreList
from rest_framework import serializers



class TitleSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Title
        fields = '__all__'

class GenreSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Genre
        fields = '__all__'


class GenreListSerializer(serializers.ModelSerializer):

    titleId = TitleSerializer(read_only=True, many=True)
    genreId = GenreSerializer(read_only=True, many=True)
    
    class Meta:
        model = GenreList
        fields = '__all__'


class UpdateTitleSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Title
        exclude=('created_at', 'totalViews')
