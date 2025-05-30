## Step0 : Install kind and helm
```bash
choco install kind
choco install kubernetes-helm
```

## Step1 :  Create a kind Cluster with a Custom Config
- Navigate to kind-config.yaml
```bash
kind create cluster --name book-club-cluster --config kind-config.yaml
```
## Step2 :  Install MetalLB for enable load-balancing locally
```bash
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.13.10/config/manifests/metallb-native.yaml
```
- Create metallb secret - one time only
```bash
kubectl create secret generic -n metallb-system memberlist --from-literal=secretkey="$(openssl rand -base64 128)"
```

```code
Note To find your kind Docker network subnet:
```
```bash
docker network inspect kind | grep Subnet
```

## Step3 : Install Ingress NGINX Controller
```bash
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
```
- Install with MetalLB support (using a LoadBalancer service):
```bash
helm install ingress-nginx ingress-nginx/ingress-nginx \
  --namespace ingress-nginx \
  --create-namespace \
  --set controller.service.type=LoadBalancer 
```
- Get the external IP assigned by MetalLB:

```bash
kubectl get svc -n ingress-nginx
```

## Step3 : Create namespace and apply all config
```bash
kubectl create namespace book-app
```
- navigate to k8s folder
```bash
kubectl apply -R -f .
```
## Step4 : Debug steps optional
```shell
kubectl get svc -n ingress-nginx
kubectl get ingress -n book-app
kubectl describe ingress book-service-ingress -n book-app
kubectl logs -n ingress-nginx -l app.kubernetes.io/name=ingress-nginx
kubectl run -it --rm curl-test -n book-app --image=curlimages/curl -- sh
curl http://book-service:4001/actuator/env
```
- Port forwarding
```shell
kubectl port-forward -n ingress-nginx service/ingress-nginx-controller 8080:80
```