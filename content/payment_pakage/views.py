from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from content_model.models import Subcription,PaidSubcription
from content_model.models import PaidSubcription
from .serializer import PaidSubcriptionSerializer,SubcriptionSerializer
import math


@api_view(['PUT','POST','DELETE'])
def paymentPakage(request):
    if request.method == 'POST':
        try:
            price = request.data.get('price')
            months = request.data.get('month')
            if not (price and months):
                return Response ({"error":"Vui lòng điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
            try:
                Subcription.objects.get(months=months)
                return Response({"error":"Gói thanh toán có thời hạn như trên đã tồn tại."},status=status.HTTP_400_BAD_REQUEST)
            except:
                data = {
                    'price' : price,
                    'months' : months
                }
                serializer = SubcriptionSerializer(data=data)
                if serializer.is_valid():
                    serializer.create(data)
                    return Response({"message":"Thêm gói thanh toán thành công."}, status=status.HTTP_200_OK)
        except:
            return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    if request.method == 'PUT':
        try:
            id = request.data.get('id')
            price = request.data.get('price')
            months = request.data.get('month')
            if not (id and price and months):
                    return Response ({"error":"Vui lòng điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
                
            data = {
                'price': price,
                'months': months
            }
            try:
                obj = Subcription.objects.get(id=id)
                serializer = SubcriptionSerializer(obj, data = data)
                
                if serializer.is_valid():
                    serializer.save()
                    return Response({"message":"Thay đổi thông tin gói thanh toán thành công."}, status=status.HTTP_200_OK)
            except:
                return Response({"error":"Gói thanh toán không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        except:
            return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    if request.method == 'DELETE':
        try:
            id = request.data.get('id')
            if not (id):
                return Response ({"error":"Vui lòng điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
            try:
                obj = Subcription.objects.get(id=id)
                obj.delete()
                return Response({"message":"Xóa gói thanh toán thành công"}, status=status.HTTP_200_OK)
            except:
                return Response({"error":"Gói thanh toán không tồn tại."},status=status.HTTP_400_BAD_REQUEST)
        except:
            return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['GET'])
def get_list_payment_packet(request):
    data = Subcription.objects.all()
    data = sorted(data, key=lambda x: (x.months))
    serializer = SubcriptionSerializer(data,many = True)
    return Response({"data":serializer.data}, status=status.HTTP_200_OK)

@api_view(['GET'])
def payment_detail_by_month(request):
    
    try:
        month = request.query_params.get('month')
        year = request.query_params.get('year')
        if not (month and year):
            return Response ({"error":"Vui lòng điền đầy đủ các trường."},status=status.HTTP_400_BAD_REQUEST)
        
        data = []
        for payment in PaidSubcription.objects.filter(start_at__year=year,start_at__month=month):
            data.append({"userid":payment.userid,"year":payment.start_at.year,"month":payment.start_at.month,"day":payment.start_at.day,"income":payment.subcriptionId.price})
        
        print(data)
        data = sorted(data, key=lambda x: (-x["month"],-x["day"]))
        
        return Response({"data":data}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)
    
@api_view(['GET'])
def statistic_payment_by_month(request):
    try:
        data = []
        
        # list chứa danh sách tháng-năm có thông tin thanh toán
        list_month_year=[]
        for payment in PaidSubcription.objects.all():
            month_year = {"month":payment.start_at.month,"year":payment.start_at.year}
            if month_year not in list_month_year:
                list_month_year.append(month_year)
                
        # Sắp xếp tháng-năm theo thứ tự hiện tại -> quá khứ
        list_month_year = sorted(list_month_year, key=lambda x: (-x["year"], -x["month"]))
        
        # tông thu nhập theo tháng-năm
        for month_year in list_month_year:
            total = 0
            for payment in PaidSubcription.objects.filter(start_at__year=month_year["year"],start_at__month=month_year["month"]):
                total += payment.subcriptionId.price
                print(payment.subcriptionId.price)
            data.append({"month":month_year['month'],"year":month_year["year"],"income":total})
            print(data)
            
        return Response({"data":data}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)

@api_view(['GET'])
def statistic_payment_by_year(request):
    try:
        data = []
        # list chứa danh sách năm có thông tin thanh toán
        list_year=[]
        for payment in PaidSubcription.objects.all():
            year = payment.start_at.year
            if year not in list_year:
                list_year.append(year)
                
        # Sắp xếp năm theo thứ tự hiện tại -> quá khứ
        list_year = sorted(list_year, reverse=True)
        
        # tông thu nhập theo năm
        for year in list_year:
            total = 0
            for payment in PaidSubcription.objects.filter(start_at__year=year):
                total += payment.subcriptionId.price
                print(payment.subcriptionId.price)
            data.append({"year":year,"income":total})
            print(data)
            
        return Response({"data":data}, status=status.HTTP_200_OK)
    except:
        return Response({"error":"Có lỗi xảy ra, hãy thử lại sau."}, status=status.HTTP_500_INTERNAL_SERVER_ERROR)