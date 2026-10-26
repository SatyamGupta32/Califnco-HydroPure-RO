package com.example.myapplication.data

import com.example.myapplication.model.Product

object ProductCatalog {

    val CATEGORIES = listOf(
        "All",
        "Purifiers",
        "Dispensers",
        "Pumps",
        "Filters",
        "Spares"
    )

    val products: List<Product> = listOf(
        // ==================== PURIFIERS (12 items) ====================
        Product(
            id = "pur_101",
            name = "HydroPure Grand RO+UV+UF+TDS Water Purifier",
            category = "Purifiers",
            description = "Advanced 8-stage water purification system with copper charge technology and 10L stainless steel storage tank.",
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=600&auto=format&fit=crop&q=80",
            mrp = 19999.0,
            retailPrice = 12999.0,
            dealerWholesalePrice = 8999.0,
            minDealerOrderQty = 5,
            rating = 4.8f,
            reviewCount = 340,
            specifications = mapOf(
                "Capacity" to "10 Litres",
                "Purification Stages" to "8 Stages (RO + UV + UF + TDS)",
                "TDS Controller" to "Yes (Up to 2000 ppm)",
                "Body Material" to "Food Grade ABS Steel",
                "Warranty" to "1 Year Onsite Warranty"
            ),
            stockQuantity = 45,
            isPopular = true
        ),
        Product(
            id = "pur_102",
            name = "AquaClean Eco Smart RO Purifier",
            category = "Purifiers",
            description = "Energy efficient RO purifier saving up to 60% water recovery with smart LED status indicators.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 15999.0,
            retailPrice = 10499.0,
            dealerWholesalePrice = 7200.0,
            minDealerOrderQty = 5,
            rating = 4.6f,
            reviewCount = 210,
            specifications = mapOf(
                "Capacity" to "8 Litres",
                "Water Saver" to "60% Recovery Rate",
                "Purification" to "RO + Mineralizer",
                "Warranty" to "1 Year"
            ),
            stockQuantity = 30,
            isPopular = true
        ),
        Product(
            id = "pur_103",
            name = "Califnco Mineral Pro Alkaline RO",
            category = "Purifiers",
            description = "Infuses essential minerals like Calcium, Magnesium, and Zinc with high pH alkaline water technology.",
            imageUrl = "https://images.unsplash.com/photo-1527661591475-527312dd65f5?w=600&auto=format&fit=crop&q=80",
            mrp = 22999.0,
            retailPrice = 14999.0,
            dealerWholesalePrice = 10500.0,
            minDealerOrderQty = 3,
            rating = 4.9f,
            reviewCount = 450,
            specifications = mapOf(
                "Capacity" to "12 Litres",
                "pH Level" to "8.5 - 9.5 Alkaline Water",
                "Purification" to "RO + Active Copper + Mineralizer",
                "Warranty" to "2 Years"
            ),
            stockQuantity = 25,
            isPopular = true
        ),
        Product(
            id = "pur_104",
            name = "HydroPure Compact Under-Sink RO System",
            category = "Purifiers",
            description = "Space-saving under-the-counter RO purifier with stylish chrome faucet and high pressure hydro tank.",
            imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&auto=format&fit=crop&q=80",
            mrp = 18499.0,
            retailPrice = 11999.0,
            dealerWholesalePrice = 8300.0,
            minDealerOrderQty = 4,
            rating = 4.7f,
            reviewCount = 180,
            specifications = mapOf(
                "Type" to "Under-Sink / Countertop",
                "Hydro Tank" to "8 Litres Pressure Tank",
                "Purification" to "RO + UV + Post Carbon",
                "Warranty" to "1 Year"
            ),
            stockQuantity = 20
        ),
        Product(
            id = "pur_105",
            name = "AquaPure Prime Wall Mount RO Purifier",
            category = "Purifiers",
            description = "Classic transparent water tank with active UV sterilization and taste enhancer cartridge.",
            imageUrl = "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=80",
            mrp = 13999.0,
            retailPrice = 8999.0,
            dealerWholesalePrice = 6100.0,
            minDealerOrderQty = 5,
            rating = 4.5f,
            reviewCount = 195,
            specifications = mapOf(
                "Capacity" to "7 Litres",
                "Purification" to "RO + UV",
                "Warranty" to "1 Year"
            ),
            stockQuantity = 40
        ),
        Product(
            id = "pur_106",
            name = "Califnco Commercial 50 LPH Heavy Duty RO Plant",
            category = "Purifiers",
            description = "High capacity 50 LPH RO water purification plant suitable for offices, restaurants, and schools.",
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600&auto=format&fit=crop&q=80",
            mrp = 38999.0,
            retailPrice = 27999.0,
            dealerWholesalePrice = 21000.0,
            minDealerOrderQty = 2,
            rating = 4.8f,
            reviewCount = 85,
            specifications = mapOf(
                "Capacity" to "50 Litres Per Hour",
                "Frame" to "Stainless Steel Commercial Frame",
                "Pumps" to "Dual Heavy Duty Booster Pumps",
                "Warranty" to "1 Year Commercial Warranty"
            ),
            stockQuantity = 12
        ),
        Product(
            id = "pur_107",
            name = "HydroPure Zenith Smart IoT RO Purifier",
            category = "Purifiers",
            description = "WiFi-enabled water purifier with live TDS monitoring, mobile app connectivity, and automatic filter life alerts.",
            imageUrl = "https://images.unsplash.com/photo-1544816155-12df9643f363?w=600&auto=format&fit=crop&q=80",
            mrp = 24999.0,
            retailPrice = 16999.0,
            dealerWholesalePrice = 12200.0,
            minDealerOrderQty = 3,
            rating = 4.9f,
            reviewCount = 290,
            specifications = mapOf(
                "Smart Features" to "WiFi + Live TDS Display",
                "Capacity" to "9 Litres",
                "Purification" to "10 Stage RO + UV + UF + Copper",
                "Warranty" to "2 Years"
            ),
            stockQuantity = 18,
            isPopular = true
        ),
        Product(
            id = "pur_108",
            name = "AquaPure UV Gravity Non-Electric Purifier",
            category = "Purifiers",
            description = "Non-electric UF gravity water purifier ideal for low TDS municipal tap water.",
            imageUrl = "https://images.unsplash.com/photo-1517646287270-a5a9ca602e5c?w=600&auto=format&fit=crop&q=80",
            mrp = 4999.0,
            retailPrice = 2999.0,
            dealerWholesalePrice = 1950.0,
            minDealerOrderQty = 10,
            rating = 4.3f,
            reviewCount = 140,
            specifications = mapOf(
                "Capacity" to "18 Litres",
                "Power Required" to "No Electricity",
                "Technology" to "UF Microfiltration Membrane"
            ),
            stockQuantity = 60
        ),
        Product(
            id = "pur_109",
            name = "Califnco Copper Max Direct Flow Tankless RO",
            category = "Purifiers",
            description = "Direct flow tankless RO purifier supplying instant purified water without water stagnation.",
            imageUrl = "https://images.unsplash.com/photo-1563170351-be82bc888aa4?w=600&auto=format&fit=crop&q=80",
            mrp = 27999.0,
            retailPrice = 18999.0,
            dealerWholesalePrice = 13800.0,
            minDealerOrderQty = 3,
            rating = 4.8f,
            reviewCount = 110,
            specifications = mapOf(
                "Flow Rate" to "2.0 Litres/Min Direct Flow",
                "Design" to "Ultra-Slim Tankless Design",
                "Purification" to "RO + UV Direct Sterilization"
            ),
            stockQuantity = 15
        ),
        Product(
            id = "pur_110",
            name = "HydroPure Shield Hot & Normal Water RO",
            category = "Purifiers",
            description = "Integrated hot water dispenser and RO purifier delivering instant 80°C hot water for tea & coffee.",
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=600&auto=format&fit=crop&q=80",
            mrp = 28999.0,
            retailPrice = 19999.0,
            dealerWholesalePrice = 14500.0,
            minDealerOrderQty = 2,
            rating = 4.7f,
            reviewCount = 230,
            specifications = mapOf(
                "Hot Water Temp" to "80°C Instant Dispensing",
                "Capacity" to "8L Normal + 2L Hot Tank",
                "Purification" to "7 Stage RO + Stainless Steel Tank"
            ),
            stockQuantity = 22
        ),
        Product(
            id = "pur_111",
            name = "AquaClean Crystal Glass Front RO Purifier",
            category = "Purifiers",
            description = "Designer toughened glass front panel purifier with ambient night light indicator.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 20999.0,
            retailPrice = 13999.0,
            dealerWholesalePrice = 9800.0,
            minDealerOrderQty = 4,
            rating = 4.6f,
            reviewCount = 95,
            specifications = mapOf(
                "Front Panel" to "Scratch Proof Toughened Glass",
                "Capacity" to "10 Litres",
                "Purification" to "RO + UV + Active Copper"
            ),
            stockQuantity = 28
        ),
        Product(
            id = "pur_112",
            name = "Califnco Commercial 100 LPH RO System",
            category = "Purifiers",
            description = "High volume commercial RO purification system with dual 4040 RO membranes.",
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600&auto=format&fit=crop&q=80",
            mrp = 64999.0,
            retailPrice = 48999.0,
            dealerWholesalePrice = 37000.0,
            minDealerOrderQty = 1,
            rating = 4.9f,
            reviewCount = 42,
            specifications = mapOf(
                "Capacity" to "100 Litres Per Hour",
                "Membranes" to "Dual High Rejection 4040 Membranes",
                "Application" to "Commercial / Industrial"
            ),
            stockQuantity = 8
        ),

        // ==================== DISPENSERS (10 items) ====================
        Product(
            id = "disp_201",
            name = "HydroPure Hot & Cold Floor Standing Water Dispenser",
            category = "Dispensers",
            description = "Heavy duty floor standing water dispenser with cooling cabinet, hot & cold taps, and child safety lock.",
            imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&auto=format&fit=crop&q=80",
            mrp = 16999.0,
            retailPrice = 11499.0,
            dealerWholesalePrice = 8200.0,
            minDealerOrderQty = 3,
            rating = 4.7f,
            reviewCount = 180,
            specifications = mapOf(
                "Type" to "Hot, Cold & Normal Water",
                "Cooling Capacity" to "3 Litres/Hour",
                "Heating Capacity" to "5 Litres/Hour",
                "Storage Cabinet" to "20 Litres Cooling Refrigerator Cabinet"
            ),
            stockQuantity = 25,
            isPopular = true
        ),
        Product(
            id = "disp_202",
            name = "AquaClean Table Top Hot & Normal Dispenser",
            category = "Dispensers",
            description = "Compact countertop table dispenser with instant heating mechanism for homes and office desks.",
            imageUrl = "https://images.unsplash.com/photo-1527661591475-527312dd65f5?w=600&auto=format&fit=crop&q=80",
            mrp = 8999.0,
            retailPrice = 5999.0,
            dealerWholesalePrice = 4100.0,
            minDealerOrderQty = 5,
            rating = 4.5f,
            reviewCount = 120,
            specifications = mapOf(
                "Placement" to "Table Top / Countertop",
                "Functions" to "Hot & Normal Water",
                "Heating Power" to "500W"
            ),
            stockQuantity = 35
        ),
        Product(
            id = "disp_203",
            name = "Califnco Bottom Loading Hot & Cold Water Dispenser",
            category = "Dispensers",
            description = "Easy bottom bottle loading water dispenser eliminate heavy lifting of water 20L jars.",
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=600&auto=format&fit=crop&q=80",
            mrp = 21999.0,
            retailPrice = 14999.0,
            dealerWholesalePrice = 10800.0,
            minDealerOrderQty = 2,
            rating = 4.8f,
            reviewCount = 210,
            specifications = mapOf(
                "Bottle Loading" to "Bottom Cabinet Slide-In",
                "Taps" to "Hot, Cold & Room Temperature",
                "Indicator" to "Empty Bottle Warning LED"
            ),
            stockQuantity = 20,
            isPopular = true
        ),
        Product(
            id = "disp_204",
            name = "HydroPure Stainless Steel Water Cooler 40L",
            category = "Dispensers",
            description = "Commercial stainless steel water cooler dispenser with fast chilling compressor and dual faucets.",
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600&auto=format&fit=crop&q=80",
            mrp = 32999.0,
            retailPrice = 23999.0,
            dealerWholesalePrice = 17500.0,
            minDealerOrderQty = 2,
            rating = 4.9f,
            reviewCount = 75,
            specifications = mapOf(
                "Cooling Tank" to "40 Litres SS304 Tank",
                "Cooling Rate" to "20 Litres/Hour",
                "Material" to "Full Stainless Steel 304"
            ),
            stockQuantity = 10
        ),
        Product(
            id = "disp_205",
            name = "AquaPure Wireless Automatic Water Can Pump Dispenser",
            category = "Dispensers",
            description = "USB rechargeable automatic electric water pump dispenser for standard 20L water cans.",
            imageUrl = "https://images.unsplash.com/photo-1517646287270-a5a9ca602e5c?w=600&auto=format&fit=crop&q=80",
            mrp = 1299.0,
            retailPrice = 699.0,
            dealerWholesalePrice = 380.0,
            minDealerOrderQty = 20,
            rating = 4.4f,
            reviewCount = 620,
            specifications = mapOf(
                "Battery" to "1200mAh USB Rechargeable",
                "Compatibility" to "20 Litre Water Cans",
                "Tube Material" to "Food Grade Silicone Tube"
            ),
            stockQuantity = 150
        ),
        Product(
            id = "disp_206",
            name = "Califnco Touchless Sensor Water Dispenser",
            category = "Dispensers",
            description = "Hygienic touchless motion sensor water dispensing unit preventing cross contamination.",
            imageUrl = "https://images.unsplash.com/photo-1563170351-be82bc888aa4?w=600&auto=format&fit=crop&q=80",
            mrp = 12999.0,
            retailPrice = 8499.0,
            dealerWholesalePrice = 5900.0,
            minDealerOrderQty = 4,
            rating = 4.6f,
            reviewCount = 90,
            specifications = mapOf(
                "Sensor Type" to "Infrared Motion Sensor",
                "Taps" to "Sensored Hot & Cold",
                "Safety" to "Auto-Cutoff Lock"
            ),
            stockQuantity = 18
        ),
        Product(
            id = "disp_207",
            name = "HydroPure Solar Powered Outdoor Water Station",
            category = "Dispensers",
            description = "Eco-friendly solar powered water cooling and dispensing unit for parks and public gardens.",
            imageUrl = "https://images.unsplash.com/photo-1544816155-12df9643f363?w=600&auto=format&fit=crop&q=80",
            mrp = 45999.0,
            retailPrice = 34999.0,
            dealerWholesalePrice = 26500.0,
            minDealerOrderQty = 1,
            rating = 4.8f,
            reviewCount = 35,
            specifications = mapOf(
                "Power" to "Solar Panel Included",
                "Capacity" to "60 Litres Stainless Tank",
                "Weatherproofing" to "IP65 Outdoor Grade"
            ),
            stockQuantity = 6
        ),
        Product(
            id = "disp_208",
            name = "AquaClean Eco Table Top Water Cooler",
            category = "Dispensers",
            description = "Thermoelectric silent cooling countertop water dispenser ideal for bedroom or study rooms.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 7499.0,
            retailPrice = 4999.0,
            dealerWholesalePrice = 3400.0,
            minDealerOrderQty = 5,
            rating = 4.3f,
            reviewCount = 115,
            specifications = mapOf(
                "Cooling Tech" to "Peltier Thermoelectric Quiet Cooling",
                "Capacity" to "3.5 Litres Internal Reservoir"
            ),
            stockQuantity = 40
        ),
        Product(
            id = "disp_209",
            name = "Califnco Commercial 80L Water Cooler with Purifier",
            category = "Dispensers",
            description = "Combo commercial water cooler with built-in 4-stage inline water filtration unit.",
            imageUrl = "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=600&auto=format&fit=crop&q=80",
            mrp = 49999.0,
            retailPrice = 37999.0,
            dealerWholesalePrice = 28500.0,
            minDealerOrderQty = 1,
            rating = 4.9f,
            reviewCount = 50,
            specifications = mapOf(
                "Capacity" to "80 Litres Cooling Tank",
                "Filtration" to "Sediment + Carbon + UV Inline Purifier"
            ),
            stockQuantity = 8
        ),
        Product(
            id = "disp_210",
            name = "HydroPure Mini Desktop Instant Kettle Dispenser",
            category = "Dispensers",
            description = "Boils water in 3 seconds with customizable temperature selection (45°C, 70°C, 85°C, 100°C).",
            imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&auto=format&fit=crop&q=80",
            mrp = 6999.0,
            retailPrice = 4499.0,
            dealerWholesalePrice = 3000.0,
            minDealerOrderQty = 6,
            rating = 4.7f,
            reviewCount = 160,
            specifications = mapOf(
                "Boiling Speed" to "3 Seconds Instant Boiling",
                "Temperatures" to "4 Preset Heat Settings",
                "Capacity" to "3 Litre Removable Tank"
            ),
            stockQuantity = 50
        ),

        // ==================== PUMPS (10 items) ====================
        Product(
            id = "pmp_301",
            name = "Califnco Heavy Duty 100 GPD RO Booster Pump",
            category = "Pumps",
            description = "High pressure 100 GPD diaphragm booster pump with pure copper winding motor and quiet vibration dampener.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 2999.0,
            retailPrice = 1899.0,
            dealerWholesalePrice = 1150.0,
            minDealerOrderQty = 10,
            rating = 4.8f,
            reviewCount = 420,
            specifications = mapOf(
                "Capacity" to "100 GPD (Gallons Per Day)",
                "Voltage" to "24V DC",
                "Pressure" to "125 PSI Working Pressure",
                "Motor" to "100% Pure Copper Winding"
            ),
            stockQuantity = 120,
            isPopular = true
        ),
        Product(
            id = "pmp_302",
            name = "HydroPure 75 GPD Compact Diaphragm Booster Pump",
            category = "Pumps",
            description = "Standard 75 GPD booster pump designed for domestic RO water purifiers with high suction capability.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 2499.0,
            retailPrice = 1499.0,
            dealerWholesalePrice = 920.0,
            minDealerOrderQty = 10,
            rating = 4.6f,
            reviewCount = 310,
            specifications = mapOf(
                "Capacity" to "75 GPD",
                "Voltage" to "24V DC",
                "Pressure" to "100 PSI"
            ),
            stockQuantity = 90
        ),
        Product(
            id = "pmp_303",
            name = "AquaClean 150 GPD High Flow Commercial RO Pump",
            category = "Pumps",
            description = "Heavy duty 150 GPD booster pump for multi-membrane commercial water purification systems.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 4499.0,
            retailPrice = 2899.0,
            dealerWholesalePrice = 1850.0,
            minDealerOrderQty = 5,
            rating = 4.8f,
            reviewCount = 175,
            specifications = mapOf(
                "Capacity" to "150 GPD High Pressure Flow",
                "Voltage" to "36V DC",
                "Working Pressure" to "140 PSI"
            ),
            stockQuantity = 45,
            isPopular = true
        ),
        Product(
            id = "pmp_304",
            name = "Califnco Self-Priming Suction Booster Pump 36V",
            category = "Pumps",
            description = "Self-priming booster pump capable of pulling water directly from low pressure overhead tanks.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 3899.0,
            retailPrice = 2499.0,
            dealerWholesalePrice = 1550.0,
            minDealerOrderQty = 8,
            rating = 4.7f,
            reviewCount = 200,
            specifications = mapOf(
                "Suction Lift" to "Up to 2 Meters",
                "Voltage" to "36V DC",
                "Flow Rate" to "1.8 Litres/Min"
            ),
            stockQuantity = 60
        ),
        Product(
            id = "pmp_305",
            name = "HydroPure Silent Silent-Glide 100 GPD Pump",
            category = "Pumps",
            description = "Ultra quiet noise isolation rubber damper booster pump designed for bedroom wall-mounted ROs.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 3299.0,
            retailPrice = 2099.0,
            dealerWholesalePrice = 1350.0,
            minDealerOrderQty = 10,
            rating = 4.9f,
            reviewCount = 280,
            specifications = mapOf(
                "Noise Level" to "< 35 dB Ultra Quiet",
                "Voltage" to "24V DC",
                "Life Span" to "50,000 Hours Motor Rating"
            ),
            stockQuantity = 75
        ),
        Product(
            id = "pmp_306",
            name = "AquaPure 300 GPD Commercial High Pressure Pump",
            category = "Pumps",
            description = "Massive 300 GPD commercial pump for 100 LPH RO purification units.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 6999.0,
            retailPrice = 4599.0,
            dealerWholesalePrice = 3100.0,
            minDealerOrderQty = 4,
            rating = 4.8f,
            reviewCount = 85,
            specifications = mapOf(
                "Capacity" to "300 GPD",
                "Voltage" to "48V DC / 240V AC Adapter",
                "Max Pressure" to "160 PSI"
            ),
            stockQuantity = 20
        ),
        Product(
            id = "pmp_307",
            name = "Califnco 24V DC High Torque Diaphragm Pump",
            category = "Pumps",
            description = "Durable replacement motor pump for high TDS borewell water purifiers.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 2799.0,
            retailPrice = 1699.0,
            dealerWholesalePrice = 1050.0,
            minDealerOrderQty = 10,
            rating = 4.5f,
            reviewCount = 140,
            specifications = mapOf(
                "TDS Capability" to "Up to 3000 ppm Water",
                "Voltage" to "24V DC"
            ),
            stockQuantity = 80
        ),
        Product(
            id = "pmp_308",
            name = "HydroPure Mini Submersible 12V Water Pump",
            category = "pumps",
            description = "Compact 12V DC mini submersible pump for small water dispensers and aquarium filtration.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 999.0,
            retailPrice = 499.0,
            dealerWholesalePrice = 280.0,
            minDealerOrderQty = 15,
            rating = 4.3f,
            reviewCount = 95,
            specifications = mapOf(
                "Voltage" to "12V DC",
                "Flow Rate" to "240 L/H"
            ),
            stockQuantity = 110
        ),
        Product(
            id = "pmp_309",
            name = "AquaClean Dual Head Commercial RO Pump Assembly",
            category = "Pumps",
            description = "Dual head booster pump configuration providing uninterrupted backup pressure.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 8999.0,
            retailPrice = 5999.0,
            dealerWholesalePrice = 4200.0,
            minDealerOrderQty = 2,
            rating = 4.9f,
            reviewCount = 40,
            specifications = mapOf(
                "Heads" to "Dual Heavy Duty Alloy Pump Heads",
                "Pressure" to "150 PSI Constant"
            ),
            stockQuantity = 15
        ),
        Product(
            id = "pmp_310",
            name = "Califnco Heavy Duty Pump Mounting Plate with Rubber Shock Dampeners",
            category = "Pumps",
            description = "Anti-vibration steel mounting bracket plate with dampener bushes for RO booster pumps.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 499.0,
            retailPrice = 249.0,
            dealerWholesalePrice = 120.0,
            minDealerOrderQty = 25,
            rating = 4.4f,
            reviewCount = 180,
            specifications = mapOf(
                "Material" to "Powder Coated Steel Plate",
                "Compatibility" to "Universal RO Pump Fit"
            ),
            stockQuantity = 200
        ),

        // ==================== FILTERS (10 items) ====================
        Product(
            id = "flt_401",
            name = "HydroPure High Rejection 80 GPD RO Membrane",
            category = "Filters",
            description = "Thin film composite (TFC) RO membrane cartridge with 98% salt rejection up to 2500 PPM TDS.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 1999.0,
            retailPrice = 999.0,
            dealerWholesalePrice = 580.0,
            minDealerOrderQty = 15,
            rating = 4.8f,
            reviewCount = 580,
            specifications = mapOf(
                "Capacity" to "80 GPD (Gallons Per Day)",
                "Salt Rejection" to "98%",
                "Max TDS" to "2500 PPM",
                "Technology" to "Polyamide Thin-Film Composite"
            ),
            stockQuantity = 150,
            isPopular = true
        ),
        Product(
            id = "flt_402",
            name = "AquaClean Spun Polypropylene Sediment Filter Cartridge (Set of 4)",
            category = "Filters",
            description = "5 Micron 10-inch spun PP sediment filter cartridges removing dust, rust, and silt particles.",
            imageUrl = "https://images.unsplash.com/photo-1527661591475-527312dd65f5?w=600&auto=format&fit=crop&q=80",
            mrp = 799.0,
            retailPrice = 399.0,
            dealerWholesalePrice = 180.0,
            minDealerOrderQty = 20,
            rating = 4.7f,
            reviewCount = 890,
            specifications = mapOf(
                "Micron Rating" to "5 Micron",
                "Length" to "10 Inches Standard",
                "Pack Size" to "Pack of 4 Cartridges"
            ),
            stockQuantity = 300,
            isPopular = true
        ),
        Product(
            id = "flt_403",
            name = "Califnco Activated Coconut Shell Carbon Block Filter",
            category = "Filters",
            description = "Premium coconut shell activated carbon filter removing chlorine, volatile organic compounds, and bad odor.",
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=600&auto=format&fit=crop&q=80",
            mrp = 899.0,
            retailPrice = 449.0,
            dealerWholesalePrice = 240.0,
            minDealerOrderQty = 15,
            rating = 4.8f,
            reviewCount = 410,
            specifications = mapOf(
                "Material" to "100% Coconut Shell Carbon",
                "Iodine Value" to "1100 IV High Absorption",
                "Length" to "10 Inches"
            ),
            stockQuantity = 210
        ),
        Product(
            id = "flt_404",
            name = "HydroPure Bio-Alkaline Mineral Cartridge",
            category = "Filters",
            description = "Post-RO inline filter cartridge that elevates pH balance and releases essential bio-minerals.",
            imageUrl = "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=600&auto=format&fit=crop&q=80",
            mrp = 1499.0,
            retailPrice = 799.0,
            dealerWholesalePrice = 450.0,
            minDealerOrderQty = 10,
            rating = 4.9f,
            reviewCount = 310,
            specifications = mapOf(
                "Function" to "pH Boost + Mineral Addition",
                "Life Span" to "6000 Litres",
                "Fitting" to "Push Fit 1/4 Inch Ports"
            ),
            stockQuantity = 140,
            isPopular = true
        ),
        Product(
            id = "flt_405",
            name = "AquaPure UF Hollow Fiber Ultrafiltration Membrane",
            category = "Filters",
            description = "0.01 Micron hollow fiber UF membrane filtering bacteria and cysts without requiring electricity.",
            imageUrl = "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=80",
            mrp = 999.0,
            retailPrice = 499.0,
            dealerWholesalePrice = 260.0,
            minDealerOrderQty = 15,
            rating = 4.6f,
            reviewCount = 230,
            specifications = mapOf(
                "Pore Size" to "0.01 Micron",
                "Housing Material" to "Food Grade Clear Housing"
            ),
            stockQuantity = 180
        ),
        Product(
            id = "flt_406",
            name = "Califnco Active Copper Charge Inline Filter",
            category = "Filters",
            description = "Infuses pure copper ions into water as recommended in Ayurveda for immune support.",
            imageUrl = "https://images.unsplash.com/photo-1517646287270-a5a9ca602e5c?w=600&auto=format&fit=crop&q=80",
            mrp = 1299.0,
            retailPrice = 699.0,
            dealerWholesalePrice = 390.0,
            minDealerOrderQty = 10,
            rating = 4.8f,
            reviewCount = 270,
            specifications = mapOf(
                "Active Element" to "Pure Copper Micro Spheres",
                "Life Span" to "4000 Litres"
            ),
            stockQuantity = 120
        ),
        Product(
            id = "flt_407",
            name = "HydroPure Complete Annual Maintenance Filter Kit (RO Membrane + 3 Filters)",
            category = "Filters",
            description = "All-in-one yearly service kit containing 80GPD Membrane, Sediment, Carbon & Post-Carbon inline filters.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 3499.0,
            retailPrice = 1899.0,
            dealerWholesalePrice = 1180.0,
            minDealerOrderQty = 8,
            rating = 4.9f,
            reviewCount = 650,
            specifications = mapOf(
                "Kit Includes" to "RO Membrane + Sediment + Carbon + Post Carbon + Elbow Connectors + Teflon Tape",
                "Compatibility" to "Universal for All RO Purifiers"
            ),
            stockQuantity = 100,
            isPopular = true
        ),
        Product(
            id = "flt_408",
            name = "AquaClean Anti-Scalant Dosing Cartridge",
            category = "Filters",
            description = "Prevents calcium scale formation on RO membranes in high hardness water areas.",
            imageUrl = "https://images.unsplash.com/photo-1527661591475-527312dd65f5?w=600&auto=format&fit=crop&q=80",
            mrp = 699.0,
            retailPrice = 349.0,
            dealerWholesalePrice = 180.0,
            minDealerOrderQty = 20,
            rating = 4.5f,
            reviewCount = 140,
            specifications = mapOf(
                "Application" to "Hard Water / High Scalant Prevention",
                "Life Span" to "6 Months"
            ),
            stockQuantity = 160
        ),
        Product(
            id = "flt_409",
            name = "Califnco Commercial 100 GPD Heavy Duty RO Membrane",
            category = "Filters",
            description = "High capacity 100 GPD membrane cartridge for light commercial and office RO plants.",
            imageUrl = "https://images.unsplash.com/photo-1585829365295-ab7cd400c167?w=600&auto=format&fit=crop&q=80",
            mrp = 2499.0,
            retailPrice = 1399.0,
            dealerWholesalePrice = 850.0,
            minDealerOrderQty = 10,
            rating = 4.8f,
            reviewCount = 190,
            specifications = mapOf(
                "Capacity" to "100 GPD",
                "Max Pressure" to "150 PSI"
            ),
            stockQuantity = 90
        ),
        Product(
            id = "flt_410",
            name = "HydroPure Post-Carbon Taste Enhancer Filter",
            category = "Filters",
            description = "Polishes purified water removing lingering taste or odor right before storage tank output.",
            imageUrl = "https://images.unsplash.com/photo-1548839140-29a749e1bc4e?w=600&auto=format&fit=crop&q=80",
            mrp = 599.0,
            retailPrice = 299.0,
            dealerWholesalePrice = 150.0,
            minDealerOrderQty = 20,
            rating = 4.6f,
            reviewCount = 220,
            specifications = mapOf(
                "Media" to "Micro Granular Silver Carbon",
                "Fitting" to "Quick Connect 1/4 Inch"
            ),
            stockQuantity = 220
        ),

        // ==================== SPARES (10 items) ====================
        Product(
            id = "spr_501",
            name = "Califnco 24V DC Auto Shut-Off Solenoid Valve (SV)",
            category = "Spares",
            description = "High reliability brass coil electromagnetic solenoid valve preventing raw water leakage into RO.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 599.0,
            retailPrice = 299.0,
            dealerWholesalePrice = 140.0,
            minDealerOrderQty = 25,
            rating = 4.7f,
            reviewCount = 490,
            specifications = mapOf(
                "Voltage" to "24V DC",
                "Port Size" to "1/4 Inch Push Fit",
                "Coil" to "100% Copper Heavy Duty Coil"
            ),
            stockQuantity = 250,
            isPopular = true
        ),
        Product(
            id = "spr_502",
            name = "HydroPure 24V 2.5A SMPS Power Supply Adapter",
            category = "Spares",
            description = "Switched-mode power supply adapter with built-in surge protection for RO pump and SV.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 999.0,
            retailPrice = 499.0,
            dealerWholesalePrice = 260.0,
            minDealerOrderQty = 15,
            rating = 4.8f,
            reviewCount = 380,
            specifications = mapOf(
                "Output" to "24V DC 2.5 Ampere",
                "Input Voltage" to "140V - 300V AC Auto Cutoff",
                "Protection" to "Short Circuit & Over Voltage"
            ),
            stockQuantity = 180,
            isPopular = true
        ),
        Product(
            id = "spr_503",
            name = "AquaClean High Pressure Switch (HPS) & Low Pressure Switch (LPS) Pair",
            category = "Spares",
            description = "Auto start/stop pressure switch sensors for automated RO purifier operation.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 699.0,
            retailPrice = 349.0,
            dealerWholesalePrice = 160.0,
            minDealerOrderQty = 20,
            rating = 4.6f,
            reviewCount = 210,
            specifications = mapOf(
                "Pack" to "1 HPS + 1 LPS Switch",
                "Connector" to "Standard Spade Terminal Pins"
            ),
            stockQuantity = 220
        ),
        Product(
            id = "spr_504",
            name = "Califnco Stainless Steel UV Barrel Assembly with 11W Lamp & Choke",
            category = "Spares",
            description = "Complete stainless steel UV chamber kit with Philips 11W UV tube and electronic ballast choke.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 1999.0,
            retailPrice = 1199.0,
            dealerWholesalePrice = 720.0,
            minDealerOrderQty = 10,
            rating = 4.9f,
            reviewCount = 320,
            specifications = mapOf(
                "UV Lamp" to "11 Watt High Intensity Germicidal Tube",
                "Chamber" to "Grade 304 Stainless Steel Barrel",
                "Power" to "230V AC Ballast"
            ),
            stockQuantity = 95,
            isPopular = true
        ),
        Product(
            id = "spr_505",
            name = "HydroPure Manual TDS Adjuster Controller Valve",
            category = "Spares",
            description = "Brass needle valve for precise TDS adjustment blending pure RO and UF mineral water.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 399.0,
            retailPrice = 199.0,
            dealerWholesalePrice = 85.0,
            minDealerOrderQty = 30,
            rating = 4.5f,
            reviewCount = 180,
            specifications = mapOf(
                "Material" to "Solid Forged Brass Needle Valve",
                "Port Size" to "1/4 Inch Push Connectors"
            ),
            stockQuantity = 300
        ),
        Product(
            id = "spr_506",
            name = "AquaPure Flow Restrictor FR-450 / FR-550 (Set of 2)",
            category = "Spares",
            description = "Precision flow restrictors maintaining optimal back pressure across the RO membrane.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 299.0,
            retailPrice = 149.0,
            dealerWholesalePrice = 55.0,
            minDealerOrderQty = 40,
            rating = 4.6f,
            reviewCount = 260,
            specifications = mapOf(
                "Rating" to "FR-450 (450cc/min)",
                "Pack" to "Pack of 2 Flow Restrictors"
            ),
            stockQuantity = 400
        ),
        Product(
            id = "spr_507",
            name = "Califnco Push Fit Pipe Elbow & Union Connector Set (24 Pcs Kit)",
            category = "Spares",
            description = "Assorted 1/4 inch quick connect fittings including elbows, tees, unions, and valve taps.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 899.0,
            retailPrice = 449.0,
            dealerWholesalePrice = 220.0,
            minDealerOrderQty = 15,
            rating = 4.8f,
            reviewCount = 410,
            specifications = mapOf(
                "Quantity" to "24 Pieces Assorted Quick Connect Fittings",
                "Material" to "Food Grade POM Plastic"
            ),
            stockQuantity = 180
        ),
        Product(
            id = "spr_508",
            name = "HydroPure Food Grade 1/4 Inch RO Water Tubing Pipe (10 Meters)",
            category = "Spares",
            description = "10 meter coil of high-pressure food grade LLDPE white water tubing pipe.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 499.0,
            retailPrice = 249.0,
            dealerWholesalePrice = 110.0,
            minDealerOrderQty = 20,
            rating = 4.7f,
            reviewCount = 330,
            specifications = mapOf(
                "Length" to "10 Meters Coil",
                "Diameter" to "1/4 Inch Outer Diameter"
            ),
            stockQuantity = 250
        ),
        Product(
            id = "spr_509",
            name = "AquaClean Heavy Duty Membrane Housing with Wrench & Connectors",
            category = "Spares",
            description = "Leak-proof thick body RO membrane housing container with spanner wrench and elbow fittings.",
            imageUrl = "https://images.unsplash.com/photo-1581092160607-ee22621dd758?w=600&auto=format&fit=crop&q=80",
            mrp = 699.0,
            retailPrice = 349.0,
            dealerWholesalePrice = 175.0,
            minDealerOrderQty = 15,
            rating = 4.7f,
            reviewCount = 270,
            specifications = mapOf(
                "Size" to "Fits All Standard 1812 / 2012 RO Membranes",
                "O-Rings" to "Dual Silicon O-Ring Leak Guard"
            ),
            stockQuantity = 190
        ),
        Product(
            id = "spr_510",
            name = "Califnco Digital Handheld Water TDS Meter Tester",
            category = "Spares",
            description = "Digital TDS meter tester with hold function and digital temperature measurement for testing water purity.",
            imageUrl = "https://images.unsplash.com/photo-1581092335397-9583fe92d232?w=600&auto=format&fit=crop&q=80",
            mrp = 799.0,
            retailPrice = 399.0,
            dealerWholesalePrice = 195.0,
            minDealerOrderQty = 20,
            rating = 4.8f,
            reviewCount = 740,
            specifications = mapOf(
                "Range" to "0 - 9990 PPM",
                "Accuracy" to "± 2% Full Scale",
                "Battery" to "2 x 1.5V Button Cells Included"
            ),
            stockQuantity = 300,
            isPopular = true
        )
    )
}
