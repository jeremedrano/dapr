# Script para probar la comunicación entre microservicios con logs exhaustivos
# Ubicación: [Ruta completa del directorio]

Write-Host "🧪 INICIANDO PRUEBAS DE COMUNICACIÓN ENTRE MICROSERVICIOS" -ForegroundColor Cyan
Write-Host "=======================================================" -ForegroundColor Cyan
Write-Host ""

# Función para hacer requests HTTP y mostrar logs
function Test-Endpoint {
    param(
        [string]$Url,
        [string]$Description
    )
    
    Write-Host "🔍 Probando: $Description" -ForegroundColor Yellow
    Write-Host "   URL: $Url" -ForegroundColor Gray
    Write-Host "   Timestamp: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss.fff')" -ForegroundColor Gray
    Write-Host ""
    
    try {
        $startTime = Get-Date
        $response = Invoke-RestMethod -Uri $Url -Method GET -ContentType "application/json" -ErrorAction Stop
        $endTime = Get-Date
        $duration = ($endTime - $startTime).TotalMilliseconds
        
        Write-Host "✅ ÉXITO - Status: 200" -ForegroundColor Green
        Write-Host "   Duración: $([math]::Round($duration, 2)) ms" -ForegroundColor Green
        Write-Host "   Response: $($response | ConvertTo-Json -Depth 3)" -ForegroundColor White
        Write-Host ""
        
        return $true
    }
    catch {
        Write-Host "❌ ERROR - Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
        Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "   Duración: $([math]::Round($duration, 2)) ms" -ForegroundColor Red
        Write-Host ""
        
        return $false
    }
}

function Test-TextEndpoint {
    param(
        [string]$Url,
        [string]$Description
    )
    
    Write-Host "🔍 Probando: $Description" -ForegroundColor Yellow
    Write-Host "   URL: $Url" -ForegroundColor Gray
    Write-Host "   Timestamp: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss.fff')" -ForegroundColor Gray
    Write-Host ""
    
    try {
        $startTime = Get-Date
        $response = Invoke-RestMethod -Uri $Url -Method GET -ContentType "text/plain" -ErrorAction Stop
        $endTime = Get-Date
        $duration = ($endTime - $startTime).TotalMilliseconds
        
        Write-Host "✅ ÉXITO - Status: 200" -ForegroundColor Green
        Write-Host "   Duración: $([math]::Round($duration, 2)) ms" -ForegroundColor Green
        Write-Host "   Response: $response" -ForegroundColor White
        Write-Host ""
        
        return $true
    }
    catch {
        Write-Host "❌ ERROR - Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
        Write-Host "   Error: $($_.Exception.Message)" -ForegroundColor Red
        Write-Host "   Duración: $([math]::Round($duration, 2)) ms" -ForegroundColor Red
        Write-Host ""
        
        return $false
    }
}

# Verificar que los servicios estén corriendo
Write-Host "🔍 Verificando que los servicios estén corriendo..." -ForegroundColor Yellow

try {
    $pokemonHealth = Invoke-RestMethod -Uri "http://localhost:8086/pokemon/hello" -Method GET -TimeoutSec 5
    Write-Host "✅ Pokemon Service (Puerto 8086): ACTIVO" -ForegroundColor Green
} catch {
    Write-Host "❌ Pokemon Service (Puerto 8086): NO RESPONDE" -ForegroundColor Red
    Write-Host "   Asegúrate de que el Pokemon Service esté corriendo" -ForegroundColor Yellow
    exit 1
}

try {
    $userHealth = Invoke-RestMethod -Uri "http://localhost:8088/users/hello" -Method GET -TimeoutSec 5
    Write-Host "✅ User-App Service (Puerto 8088): ACTIVO" -ForegroundColor Green
} catch {
    Write-Host "❌ User-App Service (Puerto 8088): NO RESPONDE" -ForegroundColor Red
    Write-Host "   Asegúrate de que el User-App Service esté corriendo" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "🚀 INICIANDO PRUEBAS DE COMUNICACIÓN" -ForegroundColor Cyan
Write-Host "===================================" -ForegroundColor Cyan
Write-Host ""

# Pruebas directas al Pokemon Service
Write-Host "📋 PRUEBAS DIRECTAS AL POKEMON SERVICE" -ForegroundColor Magenta
Write-Host "=====================================" -ForegroundColor Magenta
Write-Host ""

Test-TextEndpoint -Url "http://localhost:8086/pokemon/hello" -Description "Pokemon Service Hello"
Test-Endpoint -Url "http://localhost:8086/pokemon/random" -Description "Pokemon Aleatorio (Directo)"
Test-Endpoint -Url "http://localhost:8086/pokemon/25" -Description "Pokemon por ID (Pikachu)"
Test-Endpoint -Url "http://localhost:8086/pokemon/list" -Description "Lista de Pokemons (Directo)"

Write-Host ""
Write-Host "🔗 PRUEBAS DE COMUNICACIÓN ENTRE SERVICIOS" -ForegroundColor Magenta
Write-Host "=========================================" -ForegroundColor Magenta
Write-Host ""

# Pruebas de comunicación entre servicios
Test-TextEndpoint -Url "http://localhost:8088/users/hello" -Description "User-App Service Hello"
Test-Endpoint -Url "http://localhost:8088/users/pokemon" -Description "Pokemon Aleatorio (via User-App)"
Test-Endpoint -Url "http://localhost:8088/users/pokemon/25" -Description "Pokemon por ID (via User-App)"
Test-Endpoint -Url "http://localhost:8088/users/pokemon/list" -Description "Lista de Pokemons (via User-App)"
Test-TextEndpoint -Url "http://localhost:8088/users/pokemon-service/hello" -Description "Pokemon Service Hello (via User-App)"

Write-Host ""
Write-Host "📊 RESUMEN DE PRUEBAS" -ForegroundColor Cyan
Write-Host "====================" -ForegroundColor Cyan
Write-Host ""

Write-Host "✅ Pruebas completadas" -ForegroundColor Green
Write-Host "📝 Revisa los logs de ambos servicios para ver el flujo completo:" -ForegroundColor Yellow
Write-Host "   - Pokemon Service: Puerto 8086" -ForegroundColor Gray
Write-Host "   - User-App Service: Puerto 8088" -ForegroundColor Gray
Write-Host ""
Write-Host "🔍 Los logs mostrarán:" -ForegroundColor Yellow
Write-Host "   - Instanciación de clases" -ForegroundColor Gray
Write-Host "   - Configuración de servicios" -ForegroundColor Gray
Write-Host "   - Invocaciones HTTP" -ForegroundColor Gray
Write-Host "   - Serialización/Deserialización" -ForegroundColor Gray
Write-Host "   - Tiempos de respuesta" -ForegroundColor Gray
Write-Host "   - Errores y excepciones" -ForegroundColor Gray 