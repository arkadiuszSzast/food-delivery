kubectl port-forward service/config-server 8888 &
kubectl port-forward service/config-server-db 5432 &
kubectl port-forward service/gateway 8060 &
kubectl port-forward service/kibana 5601 &
kubectl port-forward service/server-discovery 8761 &
kubectl port-forward service/config-server 8888 &
kubectl port-forward service/account-service-db 27017 &
kubectl port-forward service/company-service-db 27016 &
