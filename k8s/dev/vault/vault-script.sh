helm repo add banzaicloud-stable https://kubernetes-charts.banzaicloud.com

kubectl create namespace vault-infra
kubectl label namespace vault-infra name=vault-infra

helm upgrade --namespace vault-infra --install vault-operator banzaicloud-stable/vault-operator --wait

kubectl apply -f rbac.yaml
kubectl apply -f cr.yaml

helm upgrade --namespace vault-infra --install vault-secrets-webhook banzaicloud-stable/vault-secrets-webhook --wait

export VAULT_TOKEN=$(kubectl get secrets vault-unseal-keys -o jsonpath={.data.vault-root} | base64 --decode)
kubectl get secret vault-tls -o jsonpath="{.data.ca\.crt}" | base64 --decode > $PWD/vault-ca.crt

export VAULT_CACERT=$PWD/vault-ca.crt
export VAULT_ADDR=https://127.0.0.1:8200
