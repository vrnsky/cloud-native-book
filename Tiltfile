custom_build(
  ref='catalog-service',
  command = 'mvn clean install -DskipTests -Dspring-boot.build-image.imageName=$EXPECTED_REF',
  deps = ['pom.xml', 'src']
)

k8s_yaml(['src/k8s/catalog-service/deployment.yml', 'src/k8s/catalog-service/service.yml'])

k8s_resource('catalog-service', port_forwards=['9091'])