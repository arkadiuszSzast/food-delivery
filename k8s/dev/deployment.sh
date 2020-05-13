kubectl apply -f ./elk-stack/elasticsearch-deployment.yaml
kubectl apply -f ./elk-stack/kibana-deployment.yaml
kubectl apply -f ./elk-stack/kibana-service.yaml
kubectl apply -f ./elk-stack/logstash-deployment.yaml

kubectl apply -f ./config-server/config-server-db-deployment.yaml
kubectl apply -f ./config-server/config-server-db-service.yaml
kubectl apply -f ./config-server/config-server-deployment.yaml
kubectl apply -f ./config-server/config-server-service.yaml

kubectl apply -f ./gateway/gateway-deployment.yaml
kubectl apply -f ./gateway/gateway-service.yaml

kubectl apply -f ./server-discovery/server-discovery-service.yaml
kubectl apply -f ./server-discovery/server-discovery-deployment.yaml

kubectl apply -f ./account-service/account-service-db-deployment.yaml
kubectl apply -f ./account-service/account-service-db-service.yaml
kubectl apply -f ./account-service/account-service-deployment.yaml

kubectl apply -f ./company-service/company-service-db-deployment.yaml
kubectl apply -f ./company-service/company-service-db-service.yaml
kubectl apply -f ./company-service/company-service-deployment.yaml

kubectl apply -f ./okta-adapter/okta-adapter-deployment.yaml

kubectl apply -f ./blackbox-exporter/blackbox-exporter-deployment.yaml
kubectl apply -f ./blackbox-exporter/blackbox-exporter-service.yaml

kubectl apply -f ./prometheus/prometheus-deployment.yaml
kubectl apply -f ./prometheus/prometheus-service.yaml

kubectl apply -f ./grafana/grafana-deployment.yaml
kubectl apply -f ./grafana/grafana-service.yaml

kubectl apply -f ./zookeeper/zookeeper-deployment.yaml
kubectl apply -f ./zookeeper/zookeeper-service.yaml

kubectl apply -f ./mail-sender/mail-sender-deployment.yaml
kubectl apply -f ./mail-sender/mail-sender-kafka-deployment.yaml
kubectl apply -f ./mail-sender/mail-sender-kafka-service.yaml

