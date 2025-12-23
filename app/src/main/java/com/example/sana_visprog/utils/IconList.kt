package com.example.sana_visprog.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class IconOption(
    val name: String,
    val icon: ImageVector
)

object IconList {
    val icons = listOf(
        IconOption("AcUnit", Icons.Default.AcUnit),
        IconOption("AccountBalance", Icons.Default.AccountBalance),
        IconOption("CofAddShoppingCartfee", Icons.Default.AddShoppingCart),
        IconOption("Anchor", Icons.Default.Anchor),
        IconOption("AllInclusive", Icons.Default.AllInclusive),
        IconOption("AttachMoney", Icons.Default.AttachMoney),
        IconOption("BeachAccess", Icons.Default.BeachAccess),
        IconOption("Bed", Icons.Default.Bed),
        IconOption("Biotech", Icons.Default.Biotech),
        IconOption("Book", Icons.Default.Book),
        IconOption("CameraAlt", Icons.Default.CameraAlt),
        IconOption("Church", Icons.Default.Church),
        IconOption("Celebration", Icons.Default.Celebration),
        IconOption("CloudQueue", Icons.Default.CloudQueue),
        IconOption("Cyclone", Icons.Default.Cyclone),
        IconOption("Deck", Icons.Default.Deck),
        IconOption("DirectionsBike", Icons.Default.DirectionsBike),
        IconOption("DirectionsCar", Icons.Default.DirectionsCar),
        IconOption("DirectionsSubway", Icons.Default.DirectionsSubway),
        IconOption("DownhillSkiing", Icons.Default.DownhillSkiing),
        IconOption("DriveEta", Icons.Default.DriveEta),
        IconOption("FitnessCenter", Icons.Default.FitnessCenter),
        IconOption("Flight", Icons.Default.Flight),
        IconOption("Hiking", Icons.Default.Hiking),
        IconOption("Hotel", Icons.Default.Hotel),
        IconOption("Landscape", Icons.Default.Landscape),
        IconOption("LocalCafe", Icons.Default.LocalCafe),
        IconOption("LocalAirport", Icons.Default.LocalAirport),
        IconOption("LocalFireDepartment", Icons.Default.LocalFireDepartment),
        IconOption("Mosque", Icons.Default.Mosque),
        IconOption("Museum", Icons.Default.Museum),
        IconOption("MusicNote", Icons.Default.MusicNote),
        IconOption("Movie", Icons.Default.Movie),
        IconOption("Nature", Icons.Default.Nature),
        IconOption("Nightlife", Icons.Default.Nightlife),
        IconOption("NightShelter", Icons.Default.NightShelter),
        IconOption("OtherHouses", Icons.Default.OtherHouses),
        IconOption("Palette", Icons.Default.Palette),
        IconOption("Park", Icons.Default.Park),
        IconOption("PermIdentity", Icons.Default.PermIdentity),
        IconOption("Pool", Icons.Default.Pool),
        IconOption("Restaurant", Icons.Default.Restaurant),
        IconOption("School", Icons.Default.School),
        IconOption("ShoppingCart", Icons.Default.ShoppingCart),
        IconOption("Skateboarding", Icons.Default.Skateboarding),
        IconOption("Spa", Icons.Default.Spa),
        IconOption("SportsEsports", Icons.Default.SportsEsports),
        IconOption("SportsSoccer", Icons.Default.SportsSoccer),
        IconOption("Train", Icons.Default.Train),
        IconOption("TwoWheeler", Icons.Default.TwoWheeler)
    )

    fun getIconByName(name: String): ImageVector {
        return icons.find { it.name == name }?.icon ?: Icons.Default.Category
    }
}