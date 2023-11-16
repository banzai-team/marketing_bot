SCRIPT_DIR=$(dirname "$0")
#
#for d in $SCRIPT_DIR/../charts/* ; do
#    helm package -u $d -d ./packaged-charts
#done

helm package -u $SCRIPT_DIR/../../helm/charts/gazprom-mrkt -d ./packaged-charts

helm repo index ./packaged-charts --url https://gazprom-charts.obs.ru-moscow-1.hc.sbercloud.ru