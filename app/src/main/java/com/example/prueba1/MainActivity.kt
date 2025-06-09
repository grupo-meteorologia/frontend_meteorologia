package com.example.prueba1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prueba1.ui_template.data.CurrentWeather
import com.example.prueba1.ui_template.data.Daily
import com.example.prueba1.ui_template.data.Hourly
import com.example.prueba1.ui_template.repository.WeatherRepository
import com.example.prueba1.ui.theme.Prueba1Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prueba1Theme {
                PantallaPrincipalClima()
            }
        }
    }
}

// Función auxiliar para obtener la imagen del clima
@Composable
fun getWeatherImage(weatherCode: Int): Painter {
    Log.d("getWeatherImage", "Recibido weatherCode: $weatherCode")
    return when (weatherCode) {
        // Códigos para cielo despejado, mayormente despejado y parcialmente nublado
        // Ahora, 0, 1 y 2 mostrarán el sol.
        0, 1, 2 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> sol.png")
            painterResource(id = R.drawable.sol)
        }
        // Código para nublado
        3 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> nube.png")
            painterResource(id = R.drawable.nube)
        }

        // Códigos para niebla (usamos la imagen de nube, o puedes crear una de niebla)
        45, 48 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> nube.png (niebla)")
            painterResource(id = R.drawable.nube) // O crea R.drawable.niebla
        }

        // Códigos para llovizna, lluvia y lluvia helada
        51, 53, 55, 56, 57, 61, 63, 65, 66, 67 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> lluvia.webp")
            painterResource(id = R.drawable.lluvia)
        }

        // Códigos para nevadas y granos de nieve
        71, 73, 75, 77 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> nieve.png")
            painterResource(id = R.drawable.nieve)
        }

        // Códigos para chubascos de lluvia (usamos lluvia)
        80, 81, 82 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> lluvia.webp")
            painterResource(id = R.drawable.lluvia)
        }

        // Códigos para chubascos de nieve (usamos nieve)
        85, 86 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> nieve.png")
            painterResource(id = R.drawable.nieve)
        }

        // Códigos para tormentas
        95, 96, 99 -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> tormenta.png")
            painterResource(id = R.drawable.tormenta)
        }

        // Por defecto, si el weatherCode no coincide con ninguno, muestra el sol.
        else -> {
            Log.d("getWeatherImage", "WeatherCode $weatherCode -> sol.png (default)")
            painterResource(id = R.drawable.sol)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PantallaPrincipalClima() {
    // Estado para almacenar los datos del clima
    var currentWeather by remember { mutableStateOf<CurrentWeather?>(null) }
    var hourlyData by remember { mutableStateOf<Hourly?>(null) }
    var dailyData by remember { mutableStateOf<Daily?>(null) }
    var locationName by remember { mutableStateOf("Cargando...") }
    val weatherRepository = remember { WeatherRepository() }

    // Fechas y formatos
    val currentCalendar = Calendar.getInstance()
    // Aseguramos el Locale en español para los nombres de los días y meses
    val spanishLocale = Locale("es", "ES")
    // **CAMBIO 1: Eliminar la hora del formato de fecha completa**
    val formatoFechaCompleta = SimpleDateFormat("dd 'de' MMMM, yyyy", spanishLocale)
    val formatoDiaSemana = SimpleDateFormat("EEEE", spanishLocale) // Formato para el nombre del día


    LaunchedEffect(Unit) {
        // La latitud y longitud son para Mendoza, Argentina.
        // Asegúrate de que estos valores sean correctos para obtener datos precisos.
        val latitude = -32.8908
        val longitude = -68.8471

        val weatherResponse = weatherRepository.fetchCurrentWeather(latitude, longitude)
        if (weatherResponse != null) {
            currentWeather = weatherResponse.currentWeather
            hourlyData = weatherResponse.hourly
            dailyData = weatherResponse.daily
            locationName = "Mendoza"
            Log.d("PantallaPrincipalClima", "Datos del clima: $currentWeather")
            Log.d("PantallaPrincipalClima", "Datos por hora: ${hourlyData?.time?.size} horas")
            Log.d("PantallaPrincipalClima", "Datos diarios: ${dailyData?.time?.size} días")
            // Aquí puedes agregar logs para ver los weathercodes específicos que estás recibiendo
            // For example:
            dailyData?.weathercode?.forEachIndexed { index, code ->
                Log.d("PantallaPrincipalClima", "Daily weathercode for day $index: $code")
            }
            hourlyData?.weathercode?.forEachIndexed { index, code ->
                Log.d("PantallaPrincipalClima", "Hourly weathercode for hour $index: $code")
            }

        } else {
            Log.e("PantallaPrincipalClima", "Error al obtener datos del clima")
            locationName = "Error al cargar"
            // Se inicializan con valores por defecto para evitar errores de null,
            // pero se podrían mostrar mensajes al usuario en la UI.
            currentWeather = CurrentWeather(0.0, 0.0, 0.0, 0, "")
            hourlyData = Hourly(emptyList(), emptyList(), emptyList())
            dailyData = Daily(emptyList(), emptyList(), emptyList(), emptyList())
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "MendoClima", color = colorResource(id = R.color.white))
                },
                colors = topAppBarColors(
                    containerColor = colorResource(id = R.color.TopBar).copy(alpha = 0.8f)
                ),
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Ajustes", tint = colorResource(id = R.color.white))
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Opción 1") },
                            onClick = { /* Lógica para la opción 1 */ }
                        )
                        DropdownMenuItem(
                            text = { Text("Opción 2") },
                            onClick = { /* Lógica para la opción 2 */ }
                        )
                        DropdownMenuItem(
                            text = { Text("Opción 3") },
                            onClick = { /* Lógica para la opción 3 */ }
                        )
                    }

                }
            )
        },
    ){ paddingValues ->

        LazyColumn(
            modifier = Modifier
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.fondo1),
                            colorResource(id = R.color.fondo2)
                        )
                    ),
                )
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                // Clima actual
                Image(
                    painter = getWeatherImage(currentWeather?.weathercode ?: 0),
                    contentDescription = "Clima actual",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .size(160.dp)
                )
                Text(text = locationName, fontSize = 20.sp, color = colorResource(id = R.color.white), textAlign = TextAlign.Center)
                Text(text = "${currentWeather?.temperature?.toInt() ?: "--"}°C", fontSize = 60.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
                // Usar el weathercode para el estado del cielo
                Text(
                    text = when(currentWeather?.weathercode) {
                        0 -> "Cielo despejado"
                        1 -> "Mayormente despejado"
                        2 -> "Parcialmente nublado"
                        3 -> "Nublado"
                        45, 48 -> "Niebla"
                        51, 53, 55 -> "Llovizna"
                        56, 57 -> "Llovizna helada"
                        61, 63, 65 -> "Lluvia"
                        66, 67 -> "Lluvia helada"
                        71, 73, 75 -> "Nevada"
                        77 -> "Granos de nieve"
                        80, 81, 82 -> "Chubascos de lluvia"
                        85, 86 -> "Chubascos de nieve"
                        95 -> "Tormenta"
                        96, 99 -> "Tormenta con granizo"
                        else -> "Desconocido"
                    },
                    fontSize = 24.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center
                )
                // Usar la fecha actual del calendario para esta visualización, sin la hora.
                Text(text = formatoFechaCompleta.format(currentCalendar.time), fontSize = 24.sp, color = colorResource(id = R.color.white), textDecoration = TextDecoration.Underline, modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
                Text(text = "El clima durante la semana: ", fontSize = 20.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 10.dp, start = 16.dp).fillMaxWidth(),textAlign = TextAlign.Left)
            }
            item {
                // Grilla de días (a partir de mañana)
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxHeight(),
                ){
                    // Filtra los días para mostrar a partir de mañana
                    dailyData?.let { daily ->
                        val today = Calendar.getInstance()
                        // Establece 'tomorrow' en la fecha de mañana, a las 00:00:00 para comparación.
                        val tomorrow = Calendar.getInstance().apply {
                            add(Calendar.DAY_OF_YEAR, 1)
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // Formato para parsear la fecha de la API

                        // Encontrar el índice del primer día que sea "mañana" o posterior
                        val startIndex = daily.time.indexOfFirst {
                            dateFormatter.parse(it)?.let { apiDate ->
                                val apiCalendar = Calendar.getInstance().apply { time = apiDate }
                                // Comparar solo el día, mes y año para asegurar que es "mañana"
                                apiCalendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR) &&
                                        apiCalendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR)
                            } ?: false
                        }

                        if (startIndex != -1) {
                            items(daily.time.size - startIndex) { index ->
                                val actualIndex = startIndex + index
                                val dayTime = daily.time[actualIndex]
                                val maxTemp = daily.temperature2mMax[actualIndex].toInt()
                                val minTemp = daily.temperature2mMin[actualIndex].toInt()
                                val weatherCode = daily.weathercode[actualIndex]

                                // Convertir la fecha a nombre de día de la semana
                                val date = dateFormatter.parse(dayTime)
                                // capitalizeFirstChar para la primera letra en mayúscula.
                                val dayName = if (date != null) formatoDiaSemana.format(date).replaceFirstChar { it.titlecase(spanishLocale) } else ""

                                Dias(
                                    dia = dayName,
                                    clima = "$maxTemp°/$minTemp", // Mostrar max/min
                                    imagen = getWeatherImage(weatherCode)
                                )
                            }
                        } else {
                            // Si no se encuentra "mañana", mostrar al menos el día actual si hay datos, o un mensaje.
                            Log.w("PantallaPrincipalClima", "No se encontraron datos para mañana en dailyData.")
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.white).copy(alpha = 0.3f),
                                    colorResource(id = R.color.white).copy(alpha = 0.3f)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Obtener el nombre del día actual para el título
                        val todayName = formatoDiaSemana.format(currentCalendar.time).replaceFirstChar { it.titlecase(spanishLocale) }
                        Text(
                            text = "$todayName - Pronóstico por hora",
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        // **CAMBIO 2: Nuevo sistema de pronóstico por hora continuo**
                        hourlyData?.let { hourly ->
                            val currentDateTime = Calendar.getInstance()
                            val apiDateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())

                            // Lista para almacenar las próximas 8 horas desde la hora actual
                            val next8Hours = mutableListOf<Triple<String, Int, Int>>() // hora, temperatura, weatherCode

                            // Buscar el índice de la hora actual o la más cercana
                            var startIndex = -1
                            for (i in hourly.time.indices) {
                                val timeString = hourly.time[i]
                                val dateTimeFromApi = apiDateTimeFormatter.parse(timeString)

                                if (dateTimeFromApi != null) {
                                    val apiCalendar = Calendar.getInstance().apply { time = dateTimeFromApi }

                                    // Si encontramos una hora igual o posterior a la actual, empezamos desde ahí
                                    if (dateTimeFromApi >= currentDateTime.time) {
                                        startIndex = i
                                        break
                                    }
                                }
                            }

                            // Si encontramos un punto de partida válido, tomamos las siguientes 8 horas
                            if (startIndex != -1 && startIndex + 8 <= hourly.time.size) {
                                for (i in startIndex until startIndex + 8) {
                                    val timeString = hourly.time[i]
                                    val dateTimeFromApi = apiDateTimeFormatter.parse(timeString)

                                    if (dateTimeFromApi != null) {
                                        val apiCalendar = Calendar.getInstance().apply { time = dateTimeFromApi }
                                        val hour = apiCalendar.get(Calendar.HOUR_OF_DAY)
                                        val temperature = hourly.temperature2m[i].toInt()
                                        val weatherCode = hourly.weathercode[i]

                                        next8Hours.add(Triple(String.format("%02d", hour), temperature, weatherCode))
                                    }
                                }
                            }

                            if (next8Hours.isEmpty()) {
                                Log.w("PantallaPrincipalClima", "No se encontraron datos horarios para las próximas 8 horas.")
                                Text(
                                    text = "No hay pronóstico por hora disponible.",
                                    color = colorResource(id = R.color.white),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            } else {
                                next8Hours.forEach { (hour, temperature, weatherCode) ->
                                    Horas(
                                        hora = hour,
                                        clima = temperature.toString(),
                                        imagen = getWeatherImage(weatherCode)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Slider con los dias de la semana
@Composable
fun Dias(dia: String, clima: String, imagen: Painter){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(90.dp)
            .padding(6.dp)
    ){
        Column(
            Modifier
                .fillMaxSize()
                .background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.cajas),
                        colorResource(id = R.color.cajas)
                    )
                ),),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = dia, color = colorResource(id=R.color.white))
            Image(painter = imagen, contentDescription = "Clima Hoy", Modifier.size(50.dp))
            Text(text = "$clima° C", fontSize = 14.sp, color = colorResource(id=R.color.white))
        }
    }
}

// Horas
@Composable
fun Horas(hora:String, clima:String, imagen:Painter){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "$hora:00",textAlign = TextAlign.Center, color = colorResource(id=R.color.white))
        Image(imagen, contentDescription = "Clima Hoy", Modifier.size(40.dp))
        Text(text = "$clima° C", textAlign = TextAlign.Center, color = colorResource(id=R.color.white))
    }
}