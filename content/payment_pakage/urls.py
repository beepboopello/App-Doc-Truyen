from django.urls import path
from . import views
urlpatterns = [
    path("paymentPakage/",views.paymentPakage),
    path("PaymentStatisticByMonth/",views.statistic_payment_by_month),
    path("PaymentStatisticByYear/",views.statistic_payment_by_year),
    path("listPaymentPacket/",views.get_list_payment_packet),
    path("History_payment_by_month/",views.payment_detail_by_month),
]