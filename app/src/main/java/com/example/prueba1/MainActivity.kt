package com.example.prueba1

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                PantallaPrincipalClima()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PantallaPrincipalClima() {
    //Variables Calendario
    val calendar = Calendar.getInstance()
    val formato = SimpleDateFormat("dd 'de' MMMM, yyyy", Locale.getDefault())
    val formatoFecha = formato.format(calendar.time)

    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        //Barra de Arriba
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

        //Contenido de la vista
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
                Image(
                    painter = painterResource(id = R.drawable.sol),
                    contentDescription = "Descripción de la imagen",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .size(160.dp)
                )
                Text(text = "Ubicación", fontSize = 20.sp, color = colorResource(id = R.color.white), textAlign = TextAlign.Center)
                Text(text = "25°C", fontSize = 60.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
                Text(text = "Soleado", fontSize = 24.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
                Text(text = formatoFecha, fontSize = 24.sp, color = colorResource(id = R.color.white), textDecoration = TextDecoration.Underline, modifier = Modifier.padding(top = 8.dp), textAlign = TextAlign.Center)
                Text(text = "El clima durante la semana: ", fontSize = 20.sp, color = colorResource(id = R.color.white), modifier = Modifier.padding(top = 10.dp, start = 16.dp).fillMaxWidth(),textAlign = TextAlign.Left)
            }
            item {
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                        .fillMaxHeight(),
                ){
                    item {
                        Dias("Lunes","25", painterResource(R.drawable.sol))
                        Dias("Martes","23", painterResource(R.drawable.viento))
                        Dias("Miercoles","20", painterResource(R.drawable.sol))
                        Dias("Jueves","15", painterResource(R.drawable.lluvia))
                        Dias("Viernes","17", painterResource(R.drawable.viento))
                        Dias("Sabado","20", painterResource(R.drawable.sol))
                        Dias("Domingo","10", painterResource(R.drawable.lluvia))
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
                        Text(
                            text = "Lunes - Pronóstico por hora",
                            color = colorResource(id = R.color.white),
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                        horas("17","23",painterResource(R.drawable.sol))
                    }
                }
            }
            item {
                Text(text = "aca va el icono jaja lol", textAlign = TextAlign.Center)
            }
        }
    }
}
//Slider con los dias de la semana
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
            Text(text = "$clima° C", color = colorResource(id=R.color.white))
        }
    }
}

@Composable
fun horas(hora:String, clima:String, imagen:Painter){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = hora+":00",textAlign = TextAlign.Center, color = colorResource(id=R.color.white))
        Image(imagen, contentDescription = "Clima Hoy", Modifier.size(40.dp))
        Text(text = clima+"° C", textAlign = TextAlign.Center, color = colorResource(id=R.color.white))
    }
}