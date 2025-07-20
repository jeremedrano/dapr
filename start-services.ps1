# Script para iniciar los microservicios con DAPR
# Ubicación: [Ruta completa del directorio]

Write-Host "🚀 Iniciando microservicios con DAPR..." -ForegroundColor Green

# Verificar si DAPR está instalado
try {
    dapr --version
    Write-Host "✅ DAPR está instalado" -ForegroundColor Green
} catch {
    Write-Host "❌ DAPR no está instalado. Por favor instala DAPR primero." -ForegroundColor Red
    Write-Host "Instrucciones: https://docs.dapr.io/getting-started/install-dapr-cli/" -ForegroundColor Yellow
    exit 1
}

# Iniciar DAPR runtime si no está corriendo
Write-Host "🔧 Iniciando DAPR runtime..." -ForegroundColor Yellow
Start-Process -FilePath "dapr" -ArgumentList "run", "--app-id", "dapr-runtime", "--dapr-http-port", "3500", "--dapr-grpc-port", "50001" -WindowStyle Hidden

# Esperar un momento para que DAPR se inicie
Start-Sleep -Seconds 3

# Iniciar el microservicio Pokemon
Write-Host "🎮 Iniciando Pokemon Service en puerto 8086..." -ForegroundColor Cyan
Start-Process -FilePath "dapr" -ArgumentList "run", "--app-id", "pokemon-service", "--app-port", "8086", "--dapr-http-port", "3501", "--dapr-grpc-port", "50002", "--", "cd", "code-user-pokemon/code-user-pokemon", "&&", "./gradlew", "quarkusDev" -WindowStyle Normal

# Esperar un momento para que el servicio Pokemon se inicie
Start-Sleep -Seconds 5

# Iniciar el microservicio User-App
Write-Host "👤 Iniciando User-App Service en puerto 8088..." -ForegroundColor Cyan
Start-Process -FilePath "dapr" -ArgumentList "run", "--app-id", "user-app-service", "--app-port", "8088", "--dapr-http-port", "3502", "--dapr-grpc-port", "50003", "--", "cd", "code-user-app/code-user-app", "&&", "./gradlew", "quarkusDev" -WindowStyle Normal

Write-Host "✅ Servicios iniciados!" -ForegroundColor Green
Write-Host ""
Write-Host "📋 Endpoints disponibles:" -ForegroundColor Yellow
Write-Host "  Pokemon Service: http://localhost:8086" -ForegroundColor White
Write-Host "  User-App Service: http://localhost:8088" -ForegroundColor White
Write-Host ""
Write-Host "🔗 Endpoints de comunicación DAPR:" -ForegroundColor Yellow
Write-Host "  GET http://localhost:8088/users/pokemon - Obtener Pokemon aleatorio" -ForegroundColor White
Write-Host "  GET http://localhost:8088/users/pokemon/{id} - Obtener Pokemon por ID" -ForegroundColor White
Write-Host "  GET http://localhost:8088/users/pokemon/list - Listar todos los Pokemons" -ForegroundColor White
Write-Host "  GET http://localhost:8088/users/pokemon-service/hello - Saludo del servicio Pokemon" -ForegroundColor White
Write-Host ""
Write-Host "🛑 Para detener los servicios, presiona Ctrl+C en cada ventana" -ForegroundColor Red 