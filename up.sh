#!/usr/bin/env bash
# Script para correr la app en un contenedor de docker
# Toma como único parámetro el número de puerto donde
# levantar el servicio

PORT=${1:-8080}
DIR=$(realpath "$(dirname "${BASH_SOURCE[0]}")")
IMAGE_NAME="resources"
CONTAINER_NAME="memo1-app-$IMAGE_NAME"

docker build -t $IMAGE_NAME "$DIR"

docker run --rm -p "$PORT":80 --name $CONTAINER_NAME $IMAGE_NAME
