package com.example.data.repository

import com.example.data.model.*

object IslamicRepository {

    val CITIES = listOf(
        CityLocation("Makkah", "Saudi Arabia", 21.4225, 39.8262, "Asia/Riyadh"),
        CityLocation("Madinah", "Saudi Arabia", 24.5247, 39.5692, "Asia/Riyadh"),
        CityLocation("Cairo", "Egypt", 30.0444, 31.2357, "Africa/Cairo"),
        CityLocation("Istanbul", "Turkey", 41.0082, 28.9784, "Europe/Istanbul"),
        CityLocation("Dubai", "United Arab Emirates", 25.2048, 55.2708, "Asia/Dubai"),
        CityLocation("Karachi", "Pakistan", 24.8607, 67.0011, "Asia/Karachi"),
        CityLocation("Lahore", "Pakistan", 31.5204, 74.3587, "Asia/Karachi"),
        CityLocation("Islamabad", "Pakistan", 33.6844, 73.0479, "Asia/Karachi"),
        CityLocation("Jakarta", "Indonesia", -6.2088, 106.8456, "Asia/Jakarta"),
        CityLocation("Kuala Lumpur", "Malaysia", 3.1390, 101.6869, "Asia/Kuala_Lumpur"),
        CityLocation("London", "United Kingdom", 51.5074, -0.1278, "Europe/London"),
        CityLocation("New York", "United States", 40.7128, -74.0060, "America/New_York"),
        CityLocation("Toronto", "Canada", 43.6532, -79.3832, "America/Toronto"),
        CityLocation("Chicago", "United States", 41.8781, -87.6298, "America/Chicago"),
        CityLocation("Los Angeles", "United States", 34.0522, -118.2437, "America/Los_Angeles"),
        CityLocation("Paris", "France", 48.8566, 2.3522, "Europe/Paris"),
        CityLocation("Berlin", "Germany", 52.5200, 13.4050, "Europe/Berlin"),
        CityLocation("Sydney", "Australia", -33.8688, 151.2093, "Australia/Sydney"),
        CityLocation("Melbourne", "Australia", -37.8136, 144.9631, "Australia/Melbourne"),
        CityLocation("Mumbai", "India", 19.0760, 72.8777, "Asia/Kolkata"),
        CityLocation("Delhi", "India", 28.6139, 77.2090, "Asia/Kolkata"),
        CityLocation("Dhaka", "Bangladesh", 23.8103, 90.4125, "Asia/Dhaka"),
        CityLocation("Doha", "Qatar", 25.2854, 51.5310, "Asia/Qatar"),
        CityLocation("Kuwait City", "Kuwait", 29.3759, 47.9774, "Asia/Kuwait"),
        CityLocation("Amman", "Jordan", 31.9454, 35.9284, "Asia/Amman"),
        CityLocation("Casablanca", "Morocco", 33.5731, -7.5898, "Africa/Casablanca"),
        CityLocation("Johannesburg", "South Africa", -26.2041, 28.0473, "Africa/Johannesburg")
    )

    val TASBEEH_PRESETS = listOf(
        TasbeehPreset(
            id = "subhanallah",
            title = "SubhanAllah",
            arabic = "سُبْحَانَ اللَّهِ",
            transliteration = "SubhanAllah",
            meaning = "Glory be to Allah",
            target = 33
        ),
        TasbeehPreset(
            id = "alhamdulillah",
            title = "Alhamdulillah",
            arabic = "الْحَمْدُ لِلَّهِ",
            transliteration = "Alhamdulillah",
            meaning = "All praise is due to Allah",
            target = 33
        ),
        TasbeehPreset(
            id = "allahuakbar",
            title = "Allahu Akbar",
            arabic = "اللَّهُ أَكْبَرُ",
            transliteration = "Allahu Akbar",
            meaning = "Allah is the Greatest",
            target = 34
        ),
        TasbeehPreset(
            id = "astaghfirullah",
            title = "Astaghfirullah",
            arabic = "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
            transliteration = "Astaghfirullaha wa atoobu ilayh",
            meaning = "I seek forgiveness of Allah and repent to Him",
            target = 100
        ),
        TasbeehPreset(
            id = "lailahaillallah",
            title = "Kalimah Tayyibah",
            arabic = "لَا إِلٰهَ إِلَّا اللَّهُ مُحَمَّدٌ رَسُولُ اللَّهِ",
            transliteration = "La ilaha illallahu Muhammadur Rasulullah",
            meaning = "There is no god but Allah, Muhammad is the Messenger of Allah",
            target = 100
        ),
        TasbeehPreset(
            id = "salawat",
            title = "Salawat (Darood)",
            arabic = "اللَّهُمَّ صَلِّ عَلَىٰ مُحَمَّدٍ وَعَلَىٰ آلِ مُحَمَّدٍ",
            transliteration = "Allahumma salli 'ala Muhammadin wa 'ala aali Muhammad",
            meaning = "O Allah, bestow peace and blessings upon Muhammad and his family",
            target = 100
        ),
        TasbeehPreset(
            id = "hawqala",
            title = "La Hawla",
            arabic = "لَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ الْعَلِيِّ الْعَظِيمِ",
            transliteration = "La hawla wa la quwwata illa billahil 'Aliyyil 'Adheem",
            meaning = "There is no power nor strength except with Allah the Most High",
            target = 33
        ),
        TasbeehPreset(
            id = "subhanallahi_bihamdihi",
            title = "SubhanAllahi wa bihamdihi",
            arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ ، سُبْحَانَ اللَّهِ الْعَظِيمِ",
            transliteration = "SubhanAllahi wa bihamdihi, SubhanAllahil 'Adheem",
            meaning = "Glory be to Allah and His is the praise, Glory be to Allah the Almighty",
            target = 100
        )
    )

    val DUAS = listOf(
        Dua(
            id = "dua_morning_1",
            category = "Morning",
            title = "Waking Up in the Morning",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
            transliteration = "Alhamdu lillahil-ladhi ahyana ba'da ma amatana wa ilayhin-nushoor.",
            english = "All praise is for Allah who gave us life after causing us to die, and unto Him is the resurrection.",
            reference = "Sahih al-Bukhari 6312",
            occasion = "Recited immediately upon opening eyes in the morning."
        ),
        Dua(
            id = "dua_morning_2",
            category = "Morning",
            title = "Sayyidul Istighfar (Chief of Prayers for Forgiveness)",
            arabic = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ ، خَلَقْتَنِي وَأَنَا عَبْدُكَ ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            transliteration = "Allahumma Anta Rabbi la ilaha illa Ant, khalaqtani wa ana 'abduk, wa ana 'ala 'ahdika wa wa'dika mastata't, a'udhu bika min sharri ma sana't, aboo'u laka bini'matika 'alayya, wa aboo'u bidhanbi faghfir li fa-innahu la yaghfirudh-dhunooba illa Ant.",
            english = "O Allah, You are my Lord, none has the right to be worshipped but You. You created me and I am Your slave. I abide by Your covenant as best as I can. I seek refuge with You from the evil of what I have done. I acknowledge Your blessings upon me and my sin, so forgive me, for none forgives sins except You.",
            reference = "Sahih al-Bukhari 6306",
            occasion = "If recited with conviction in morning/evening and dies, enters Paradise."
        ),
        Dua(
            id = "dua_evening_1",
            category = "Evening",
            title = "Evening Protection & Surrender",
            arabic = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ ، وَالْحَمْدُ لِلَّهِ ، لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ",
            transliteration = "Amsayna wa amsal-mulku lillah, wal-hamdu lillah, la ilaha illallahu wahdahu la shareeka lah.",
            english = "We have reached the evening and with it the whole kingdom belongs to Allah, and all praise is for Allah. None has the right to be worshipped but Allah alone, who has no partner.",
            reference = "Sahih Muslim 2723",
            occasion = "Evening supplication after Asr/Maghrib."
        ),
        Dua(
            id = "dua_salah_1",
            category = "Salah",
            title = "Opening Supplication (Dua al-Istiftah)",
            arabic = "سُبْحَانَكَ اللَّهُمَّ وَبِحَمْدِكَ ، وَتَبَارَكَ اسْمُكَ ، وَتَعَالَى جَدُّكَ ، وَلَا إِلَهَ غَيْرُكَ",
            transliteration = "Subhanaka Allahumma wa bihamdika, wa tabarakasmuka, wa ta'ala jadduka, wa la ilaha ghayruk.",
            english = "Glory be to You, O Allah, and all praise. Blessed is Your Name, exalted is Your majesty, and there is none worthy of worship besides You.",
            reference = "Sunan Abu Dawud 775",
            occasion = "Recited immediately after Takbiratul Ihram before Surah Al-Fatihah."
        ),
        Dua(
            id = "dua_salah_2",
            category = "Salah",
            title = "Supplication in Sujood (Prostration)",
            arabic = "سُبْحَانَ رَبِّيَ الأَعْلَى وَبِحَمْدِهِ",
            transliteration = "Subhana Rabbiyal-A'la wa bihamdih.",
            english = "Glory be to my Lord, the Most High, and praise be to Him.",
            reference = "Sahih Muslim 772",
            occasion = "Recited at least three times in every prostration."
        ),
        Dua(
            id = "dua_sleep_1",
            category = "Sleep",
            title = "Before Going to Sleep",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            transliteration = "Bismika Allahumma amootu wa ahya.",
            english = "In Your Name, O Allah, I die and I live.",
            reference = "Sahih al-Bukhari 6324",
            occasion = "Recited when lying down on the right side."
        ),
        Dua(
            id = "dua_travel_1",
            category = "Travel",
            title = "Dua for Travelling / Journey",
            arabic = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ",
            transliteration = "Subhanalladhi sakh-khara lana hadha wa ma kunna lahu muqrineen, wa inna ila Rabbina lamunqaliboon.",
            english = "Glory to Him who has brought this under our control though we were unable to subdue it by ourselves, and indeed, to our Lord we will surely return.",
            reference = "Surah Az-Zukhruf (43:13-14) / Sahih Muslim 1342",
            occasion = "Recited when embarking on vehicle, flight, or journey."
        ),
        Dua(
            id = "dua_food_1",
            category = "Food",
            title = "Before Starting a Meal",
            arabic = "بِسْمِ اللَّهِ وَعَلَى بَرَكَةِ اللَّهِ",
            transliteration = "Bismillahi wa 'ala barakatillah.",
            english = "In the Name of Allah and with the blessings of Allah.",
            reference = "Al-Mustadrak al-Hakim",
            occasion = "Recited before taking the first bite."
        ),
        Dua(
            id = "dua_food_2",
            category = "Food",
            title = "After Finishing Food",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَذَا وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
            transliteration = "Alhamdu lillahil-ladhi at'amani hadha wa razaqanihi min ghayri hawlin minni wa la quwwah.",
            english = "Praise be to Allah Who has fed me this and provided it for me without any power or strength on my part.",
            reference = "Jami` at-Tirmidhi 3458",
            occasion = "Sins are forgiven for the one who recites this after eating."
        ),
        Dua(
            id = "dua_protection_1",
            category = "Protection",
            title = "Dua Against Harm & Evil Eye",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            transliteration = "Bismillahi alladhi la yadurru ma'as-mihi shay'un fil-ardi wa la fis-sama'i wa Huwas-Sami'ul-'Aleem.",
            english = "In the Name of Allah, with Whose Name nothing can cause harm in the earth nor in the heavens, and He is the All-Hearing, the All-Knowing.",
            reference = "Sunan Abu Dawud 5088",
            occasion = "Recite 3 times morning and evening for total divine protection."
        ),
        Dua(
            id = "dua_forgiveness_1",
            category = "Forgiveness",
            title = "Dua of Prophet Yunus (A.S) in the Whale",
            arabic = "لَا إِلَهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
            transliteration = "La ilaha illa Anta subhanaka inni kuntu minaz-zalimeen.",
            english = "There is no deity except You; exalted are You. Indeed, I have been of the wrongdoers.",
            reference = "Surah Al-Anbiya (21:87) / Jami` at-Tirmidhi 3505",
            occasion = "No Muslim calls upon Allah with this dua during distress except that He relieves them."
        )
    )

    val HADITH_LIST = listOf(
        HadithItem(
            id = "hadith_1",
            collection = "Sahih al-Bukhari",
            hadithNumber = "1",
            chapter = "Revelation & Intentions",
            narrator = "Umar bin Al-Khattab (R.A)",
            arabic = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
            english = "Actions are judged by intentions, and every person will get what they intended.",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_2",
            collection = "Sahih al-Bukhari",
            hadithNumber = "528",
            chapter = "Prayer Times",
            narrator = "Abdullah bin Mas'ud (R.A)",
            arabic = "سَأَلْتُ النَّبِيَّ ﷺ : أَيُّ الْعَمَلِ أَحَبُّ إِلَى اللَّهِ ؟ قَالَ : الصَّلاَةُ عَلَى وَقْتِهَا",
            english = "I asked the Prophet ﷺ: 'Which deed is the most beloved to Allah?' He replied: 'To perform the prayers at their proper fixed times.'",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_3",
            collection = "Sahih Muslim",
            hadithNumber = "223",
            chapter = "Purification",
            narrator = "Abu Malik Al-Ash'ari (R.A)",
            arabic = "الطُّهُورُ شَطْرُ الإِيمَانِ ، وَالْحَمْدُ لِلَّهِ تَمْلأُ الْمِيزَانَ",
            english = "Cleanliness is half of faith, and 'Alhamdulillah' (Praise be to Allah) fills the scale of good deeds.",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_4",
            collection = "40 Hadith Nawawi",
            hadithNumber = "13",
            chapter = "Brotherhood & Faith",
            narrator = "Anas bin Malik (R.A)",
            arabic = "لاَ يُؤْمِنُ أَحَدُكُمْ حَتَّى يُحِبَّ لأَخِيهِ مَا يُحِبُّ لِنَفْسِهِ",
            english = "None of you truly believes until he loves for his brother what he loves for himself.",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_5",
            collection = "Sahih al-Bukhari",
            hadithNumber = "6018",
            chapter = "Good Manners",
            narrator = "Abu Hurairah (R.A)",
            arabic = "مَنْ كَانَ يُؤْمِنُ بِاللَّهِ وَالْيَوْمِ الآخِرِ فَلْيَقُلْ خَيْرًا أَوْ لِيَصْمُتْ",
            english = "Whoever believes in Allah and the Last Day should either speak good or remain silent.",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_6",
            collection = "Jami` at-Tirmidhi",
            hadithNumber = "1987",
            chapter = "Righteousness & Charity",
            narrator = "Abu Dharr (R.A)",
            arabic = "تَبَسُّمُكَ فِي وَجْهِ أَخِيكَ لَكَ صَدَقَةٌ",
            english = "Your smiling in the face of your brother is charity for you.",
            grade = "Hasan-Sahih"
        ),
        HadithItem(
            id = "hadith_7",
            collection = "Sahih Muslim",
            hadithNumber = "2588",
            chapter = "Kindness & Mercy",
            narrator = "Jarir bin Abdullah (R.A)",
            arabic = "مَنْ لاَ يَرْحَمِ النَّاسَ لاَ يَرْحَمْهُ اللَّهُ",
            english = "He who is not merciful to the people, Allah will not be merciful to him.",
            grade = "Sahih"
        ),
        HadithItem(
            id = "hadith_8",
            collection = "Sunan Abu Dawud",
            hadithNumber = "1683",
            chapter = "Zakat & Wealth",
            narrator = "Abu Hurairah (R.A)",
            arabic = "مَا نَقَصَتْ صَدَقَةٌ مِنْ مَالٍ ، وَمَا زَادَ اللَّهُ عَبْدًا بِعَفْوٍ إِلاَّ عِزًّا",
            english = "Charity does not decrease wealth, and Allah increases the honor of the one who forgives.",
            grade = "Sahih"
        )
    )

    val ISLAMIC_EVENTS = listOf(
        IslamicEvent(
            name = "Islamic New Year (1st Muharram)",
            arabicName = "رأس السنة الهجرية",
            hijriDate = "1 Muharram 1448",
            description = "Commemorates the Hijrah (migration) of Prophet Muhammad ﷺ from Makkah to Madinah."
        ),
        IslamicEvent(
            name = "Day of Ashura",
            arabicName = "يوم عاشوراء",
            hijriDate = "10 Muharram 1448",
            description = "The day Prophet Musa (A.S) and Banu Israel were saved from Pharaoh; highly recommended to fast."
        ),
        IslamicEvent(
            name = "Mawlid an-Nabi",
            arabicName = "المولد النبوي الشريف",
            hijriDate = "12 Rabi' al-Awwal 1448",
            description = "Commemoration of the blessed birth of the Prophet of Mercy, Muhammad ﷺ."
        ),
        IslamicEvent(
            name = "Isra and Mi'raj",
            arabicName = "الإسراء والمعراج",
            hijriDate = "27 Rajab 1447",
            description = "The miraculous night journey and heavenly ascension where the five daily prayers were ordained."
        ),
        IslamicEvent(
            name = "Mid-Sha'ban (Laylat al-Bara'ah)",
            arabicName = "ليلة النصف من شعبان",
            hijriDate = "15 Sha'ban 1447",
            description = "A night of divine mercy, forgiveness, and preparation for the holy month of Ramadan."
        ),
        IslamicEvent(
            name = "First Day of Ramadan",
            arabicName = "أول أيام شهر رمضان المبارك",
            hijriDate = "1 Ramadan 1447",
            description = "Beginning of the blessed month of fasting, intense Quran recitation, and night prayers (Taraweeh).",
            isUpcomingInRamadan = true
        ),
        IslamicEvent(
            name = "Laylat al-Qadr (Night of Power)",
            arabicName = "ليلة القدر المباركة",
            hijriDate = "27 Ramadan 1447",
            description = "A night better than a thousand months (83 years). The Quran was first revealed on this night.",
            isUpcomingInRamadan = true
        ),
        IslamicEvent(
            name = "Eid al-Fitr",
            arabicName = "عيد الفطر المبارك",
            hijriDate = "1 Shawwal 1447",
            description = "Joyous Islamic celebration marking the conclusion of the holy month of fasting."
        ),
        IslamicEvent(
            name = "Day of Arafah",
            arabicName = "يوم عرفة",
            hijriDate = "9 Dhu al-Hijjah 1447",
            description = "The pinnacle day of the Hajj pilgrimage. Fasting expiates sins of the previous and coming year."
        ),
        IslamicEvent(
            name = "Eid al-Adha",
            arabicName = "عيد الأضحى المبارك",
            hijriDate = "10 Dhu al-Hijjah 1447",
            description = "Feast of the Sacrifice honoring the profound devotion of Prophet Ibrahim (A.S) and Ismail (A.S)."
        )
    )

    val SURAHS = listOf(
        SurahInfo(1, "الفاتحة", "Al-Fatihah", "The Opener", 7, "Meccan", 1),
        SurahInfo(2, "البقرة", "Al-Baqarah", "The Cow", 286, "Medinan", 1),
        SurahInfo(3, "آل عمران", "Ali 'Imran", "Family of Imran", 200, "Medinan", 3),
        SurahInfo(4, "النساء", "An-Nisa", "The Women", 176, "Medinan", 4),
        SurahInfo(5, "المائدة", "Al-Ma'idah", "The Table Spread", 120, "Medinan", 6),
        SurahInfo(6, "الأنعام", "Al-An'am", "The Cattle", 165, "Meccan", 7),
        SurahInfo(7, "الأعراف", "Al-A'raf", "The Heights", 206, "Meccan", 8),
        SurahInfo(8, "الأنفال", "Al-Anfal", "The Spoils of War", 75, "Medinan", 9),
        SurahInfo(9, "التوبة", "At-Tawbah", "The Repentance", 129, "Medinan", 10),
        SurahInfo(10, "يونس", "Yunus", "Jonah", 109, "Meccan", 11),
        SurahInfo(11, "هود", "Hud", "Hud", 123, "Meccan", 11),
        SurahInfo(12, "يوسف", "Yusuf", "Joseph", 111, "Meccan", 12),
        SurahInfo(13, "الرعد", "Ar-Ra'd", "The Thunder", 43, "Medinan", 13),
        SurahInfo(14, "إبراهيم", "Ibrahim", "Abraham", 52, "Meccan", 13),
        SurahInfo(15, "الحجر", "Al-Hijr", "The Rocky Tract", 99, "Meccan", 14),
        SurahInfo(16, "النحل", "An-Nahl", "The Bee", 128, "Meccan", 14),
        SurahInfo(17, "الإسراء", "Al-Isra", "The Night Journey", 111, "Meccan", 15),
        SurahInfo(18, "الكهف", "Al-Kahf", "The Cave", 110, "Meccan", 15),
        SurahInfo(19, "مريم", "Maryam", "Mary", 98, "Meccan", 16),
        SurahInfo(20, "طه", "Taha", "Ta-Ha", 135, "Meccan", 16),
        SurahInfo(21, "الأنبياء", "Al-Anbiya", "The Prophets", 112, "Meccan", 17),
        SurahInfo(22, "الحج", "Al-Hajj", "The Pilgrimage", 78, "Medinan", 17),
        SurahInfo(23, "المؤمنون", "Al-Mu'minun", "The Believers", 118, "Meccan", 18),
        SurahInfo(24, "النور", "An-Nur", "The Light", 64, "Medinan", 18),
        SurahInfo(25, "الفرقان", "Al-Furqan", "The Criterion", 77, "Meccan", 18),
        SurahInfo(26, "الشعراء", "Ash-Shu'ara", "The Poets", 227, "Meccan", 19),
        SurahInfo(27, "النمل", "An-Naml", "The Ant", 93, "Meccan", 19),
        SurahInfo(28, "القصص", "Al-Qasas", "The Stories", 88, "Meccan", 20),
        SurahInfo(29, "العنكبوت", "Al-'Ankabut", "The Spider", 69, "Meccan", 20),
        SurahInfo(30, "الروم", "Ar-Rum", "The Romans", 60, "Meccan", 21),
        SurahInfo(31, "لقمان", "Luqman", "Luqman", 34, "Meccan", 21),
        SurahInfo(32, "السجدة", "As-Sajdah", "The Prostration", 30, "Meccan", 21),
        SurahInfo(33, "الأحزاب", "Al-Ahzab", "The Combined Forces", 73, "Medinan", 21),
        SurahInfo(34, "سبأ", "Saba", "Sheba", 54, "Meccan", 22),
        SurahInfo(35, "فاطر", "Fatir", "Originator", 45, "Meccan", 22),
        SurahInfo(36, "يس", "Ya-Sin", "Ya-Sin", 83, "Meccan", 22),
        SurahInfo(37, "الصافات", "As-Saffat", "Those Who Set The Ranks", 182, "Meccan", 23),
        SurahInfo(38, "ص", "Sad", "The Letter Sad", 88, "Meccan", 23),
        SurahInfo(39, "الزمر", "Az-Zumar", "The Troops", 75, "Meccan", 23),
        SurahInfo(40, "غافر", "Ghafir", "The Forgiver", 85, "Meccan", 24),
        SurahInfo(41, "فصلت", "Fussilat", "Explained in Detail", 54, "Meccan", 24),
        SurahInfo(42, "الشورى", "Ash-Shura", "The Consultation", 53, "Meccan", 25),
        SurahInfo(43, "الزخرف", "Az-Zukhruf", "The Ornaments of Gold", 89, "Meccan", 25),
        SurahInfo(44, "الدخان", "Ad-Dukhan", "The Smoke", 59, "Meccan", 25),
        SurahInfo(45, "الجاثية", "Al-Jathiyah", "The Crouching", 37, "Meccan", 25),
        SurahInfo(46, "الأحقاف", "Al-Ahqaf", "The Wind-Curved Sandhills", 35, "Meccan", 26),
        SurahInfo(47, "محمد", "Muhammad", "Muhammad", 38, "Medinan", 26),
        SurahInfo(48, "الفتح", "Al-Fath", "The Victory", 29, "Medinan", 26),
        SurahInfo(49, "الحجرات", "Al-Hujurat", "The Rooms", 18, "Medinan", 26),
        SurahInfo(50, "ق", "Qaf", "The Letter Qaf", 45, "Meccan", 26),
        SurahInfo(51, "الذاريات", "Adh-Dhariyat", "The Winnowing Winds", 60, "Meccan", 26),
        SurahInfo(52, "الطور", "At-Tur", "The Mount", 49, "Meccan", 27),
        SurahInfo(53, "النجم", "An-Najm", "The Star", 62, "Meccan", 27),
        SurahInfo(54, "القمر", "Al-Qamar", "The Moon", 55, "Meccan", 27),
        SurahInfo(55, "الرحمن", "Ar-Rahman", "The Beneficent", 78, "Medinan", 27),
        SurahInfo(56, "الواقعة", "Al-Waqi'ah", "The Inevitable", 96, "Meccan", 27),
        SurahInfo(57, "الحديد", "Al-Hadid", "The Iron", 29, "Medinan", 27),
        SurahInfo(58, "المجادلة", "Al-Mujadila", "The Pleading Woman", 22, "Medinan", 28),
        SurahInfo(59, "الحشر", "Al-Hashr", "The Exile", 24, "Medinan", 28),
        SurahInfo(60, "الممتحنة", "Al-Mumtahanah", "She That Is To Be Examined", 13, "Medinan", 28),
        SurahInfo(61, "الصف", "As-Saff", "The Ranks", 14, "Medinan", 28),
        SurahInfo(62, "الجمعة", "Al-Jumu'ah", "The Congregation (Friday)", 11, "Medinan", 28),
        SurahInfo(63, "المنافقون", "Al-Munafiqun", "The Hypocrites", 11, "Medinan", 28),
        SurahInfo(64, "التغابن", "At-Taghabun", "The Mutual Disillusion", 18, "Medinan", 28),
        SurahInfo(65, "الطلاق", "At-Talaq", "The Divorce", 12, "Medinan", 28),
        SurahInfo(66, "التحريم", "At-Tahrim", "The Prohibition", 12, "Medinan", 28),
        SurahInfo(67, "الملك", "Al-Mulk", "The Sovereignty", 30, "Meccan", 29),
        SurahInfo(68, "القلم", "Al-Qalam", "The Pen", 52, "Meccan", 29),
        SurahInfo(69, "الحاقة", "Al-Haqqah", "The Inevitable Reality", 52, "Meccan", 29),
        SurahInfo(70, "المعارج", "Al-Ma'arij", "The Ascending Stairways", 44, "Meccan", 29),
        SurahInfo(71, "نوح", "Nuh", "Noah", 28, "Meccan", 29),
        SurahInfo(72, "الجن", "Al-Jinn", "The Jinn", 28, "Meccan", 29),
        SurahInfo(73, "المزمل", "Al-Muzzammil", "The Enshrouded One", 20, "Meccan", 29),
        SurahInfo(74, "المدثر", "Al-Muddaththir", "The Cloaked One", 56, "Meccan", 29),
        SurahInfo(75, "القيامة", "Al-Qiyamah", "The Resurrection", 40, "Meccan", 29),
        SurahInfo(76, "الإنسان", "Al-Insan", "Man", 31, "Medinan", 29),
        SurahInfo(77, "المرسلات", "Al-Mursalat", "The Emissaries", 50, "Meccan", 29),
        SurahInfo(78, "النبأ", "An-Naba", "The Tidings", 40, "Meccan", 30),
        SurahInfo(79, "النازعات", "An-Nazi'at", "Those Who Drag Forth", 46, "Meccan", 30),
        SurahInfo(80, "عبس", "Abasa", "He Frowned", 42, "Meccan", 30),
        SurahInfo(81, "التكوير", "At-Takwir", "The Overthrowing", 29, "Meccan", 30),
        SurahInfo(82, "الانفطار", "Al-Infitar", "The Cleaving", 19, "Meccan", 30),
        SurahInfo(83, "المطففين", "Al-Mutaffifin", "The Defrauding", 36, "Meccan", 30),
        SurahInfo(84, "الانشقاق", "Al-Inshiqaq", "The Splitting Open", 25, "Meccan", 30),
        SurahInfo(85, "البروج", "Al-Buruj", "The Mansions of the Stars", 22, "Meccan", 30),
        SurahInfo(86, "الطارق", "At-Tariq", "The Morning Star", 17, "Meccan", 30),
        SurahInfo(87, "الأعلى", "Al-A'la", "The Most High", 19, "Meccan", 30),
        SurahInfo(88, "الغاشية", "Al-Ghashiyah", "The Overwhelming Event", 26, "Meccan", 30),
        SurahInfo(89, "الفجر", "Al-Fajr", "The Dawn", 30, "Meccan", 30),
        SurahInfo(90, "البلد", "Al-Balad", "The City", 20, "Meccan", 30),
        SurahInfo(91, "الشمس", "Ash-Shams", "The Sun", 15, "Meccan", 30),
        SurahInfo(92, "الليل", "Al-Layl", "The Night", 21, "Meccan", 30),
        SurahInfo(93, "الضحى", "Ad-Duha", "The Morning Hours", 11, "Meccan", 30),
        SurahInfo(94, "الشرح", "Ash-Sharh", "The Relief", 8, "Meccan", 30),
        SurahInfo(95, "التين", "At-Tin", "The Fig", 8, "Meccan", 30),
        SurahInfo(96, "العلق", "Al-'Alaq", "The Clot", 19, "Meccan", 30),
        SurahInfo(97, "القدر", "Al-Qadr", "The Power", 5, "Meccan", 30),
        SurahInfo(98, "البينة", "Al-Bayyinah", "The Clear Proof", 8, "Medinan", 30),
        SurahInfo(99, "الزلزلة", "Az-Zalzalah", "The Earthquake", 8, "Medinan", 30),
        SurahInfo(100, "العاديات", "Al-'Adiyat", "The Courser", 11, "Meccan", 30),
        SurahInfo(101, "القارعة", "Al-Qari'ah", "The Calamity", 11, "Meccan", 30),
        SurahInfo(102, "التكاثر", "At-Takathur", "The Rivalry In World Increase", 8, "Meccan", 30),
        SurahInfo(103, "العصر", "Al-'Asr", "The Declining Day", 3, "Meccan", 30),
        SurahInfo(104, "الهمزة", "Al-Humazah", "The Traducer", 9, "Meccan", 30),
        SurahInfo(105, "الفيل", "Al-Fil", "The Elephant", 5, "Meccan", 30),
        SurahInfo(106, "قريش", "Quraysh", "Quraysh", 4, "Meccan", 30),
        SurahInfo(107, "الماعون", "Al-Ma'un", "The Small Kindnesses", 7, "Meccan", 30),
        SurahInfo(108, "الكوثر", "Al-Kawthar", "The Abundance", 3, "Meccan", 30),
        SurahInfo(109, "الكافرون", "Al-Kafirun", "The Disbelievers", 6, "Meccan", 30),
        SurahInfo(110, "النصر", "An-Nasr", "The Divine Support", 3, "Medinan", 30),
        SurahInfo(111, "المسد", "Al-Masad", "The Palm Fibre", 5, "Meccan", 30),
        SurahInfo(112, "الإخلاص", "Al-Ikhlas", "The Sincerity", 4, "Meccan", 30),
        SurahInfo(113, "الفلق", "Al-Falaq", "The Daybreak", 5, "Meccan", 30),
        SurahInfo(114, "الناس", "An-Nas", "Mankind", 6, "Meccan", 30)
    )

    val JUZ_LIST = listOf(
        JuzInfo(1, "الم", "Alif Lam Meem", "Al-Fatihah", 1),
        JuzInfo(2, "سيقول", "Sayaqool", "Al-Baqarah", 142),
        JuzInfo(3, "تلك الرسل", "Tilkar Rusul", "Al-Baqarah", 253),
        JuzInfo(4, "لن تنالوا", "Lan Tanaalu", "Ali 'Imran", 93),
        JuzInfo(5, "والمحصنات", "Wal Muhsanat", "An-Nisa", 24),
        JuzInfo(6, "لا يحب الله", "La Yuhibbullah", "An-Nisa", 148),
        JuzInfo(7, "وإذا سمعوا", "Wa Iza Sami'u", "Al-Ma'idah", 82),
        JuzInfo(8, "ولو أننا", "Wa Law Annana", "Al-An'am", 111),
        JuzInfo(9, "قال الملأ", "Qalal Mala'u", "Al-A'raf", 88),
        JuzInfo(10, "واعلموا", "Wa'lamu", "Al-Anfal", 41),
        JuzInfo(11, "يعتذرون", "Ya'taziroon", "At-Tawbah", 93),
        JuzInfo(12, "وما من دابة", "Wa Ma Min Dabbah", "Hud", 6),
        JuzInfo(13, "وما أبرئ", "Wa Ma Ubarri'u", "Yusuf", 53),
        JuzInfo(14, "ربما", "Rubama", "Al-Hijr", 1),
        JuzInfo(15, "سبحان الذي", "Subhanallazi", "Al-Isra", 1),
        JuzInfo(16, "قال ألم", "Qala Alam", "Al-Kahf", 75),
        JuzInfo(17, "اقترب للناس", "Iqtaraba Lin Nasi", "Al-Anbiya", 1),
        JuzInfo(18, "قد أفلح", "Qad Aflaha", "Al-Mu'minun", 1),
        JuzInfo(19, "وقال الذين", "Wa Qalal Lazina", "Al-Furqan", 21),
        JuzInfo(20, "أمن خلق", "Amman Khalaqa", "An-Naml", 56),
        JuzInfo(21, "اتل ما أوحي", "Utlu Ma Oohiya", "Al-'Ankabut", 46),
        JuzInfo(22, "ومن يقنت", "Wa Man Yaqnut", "Al-Ahzab", 31),
        JuzInfo(23, "وما لي", "Wa Maliya", "Ya-Sin", 28),
        JuzInfo(24, "فمن أظلم", "Fa Man Azlamu", "Az-Zumar", 32),
        JuzInfo(25, "إليه يرد", "Ilaihi Yuraddu", "Fussilat", 47),
        JuzInfo(26, "حم", "Ha-Meem", "Al-Ahqaf", 1),
        JuzInfo(27, "قال فما خطبكم", "Qala Fama Khatbukum", "Adh-Dhariyat", 31),
        JuzInfo(28, "قد سمع الله", "Qad Sami'allah", "Al-Mujadila", 1),
        JuzInfo(29, "تبارك الذي", "Tabarakallazi", "Al-Mulk", 1),
        JuzInfo(30, "عم يتساءلون", "'Amma Yatasa'aloon", "An-Naba", 1)
    )

    // Curated Ayat for Reader View
    fun getAyahsForSurah(surahNumber: Int): List<AyahItem> {
        return when (surahNumber) {
            1 -> listOf(
                AyahItem(1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Bismillahir-Rahmanir-Raheem", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
                AyahItem(1, 2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Alhamdu lillahi Rabbil-'alameen", "[All] praise is [due] to Allah, Lord of the worlds -"),
                AyahItem(1, 3, "الرَّحْمَٰنِ الرَّحِيمِ", "Ar-Rahmanir-Raheem", "The Entirely Merciful, the Especially Merciful,"),
                AyahItem(1, 4, "مَالِكِ يَوْمِ الدِّينِ", "Maliki yawmid-deen", "Sovereign of the Day of Recompense."),
                AyahItem(1, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "Iyyaka na'budu wa iyyaka nasta'een", "It is You we worship and You we ask for help."),
                AyahItem(1, 6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Ihdinas-siratal-mustaqeem", "Guide us to the straight path -"),
                AyahItem(1, 7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "Siratal-ladheena an'amta 'alayhim ghayril-maghdoobi 'alayhim wa lad-daalleen", "The path of those upon whom You have bestowed favor, not of those who have evoked [Your] anger or of those who are astray.")
            )
            112 -> listOf(
                AyahItem(112, 1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "Qul Huwallahu Ahad", "Say, 'He is Allah, [who is] One,"),
                AyahItem(112, 2, "اللَّهُ الصَّمَدُ", "Allahus-Samad", "Allah, the Eternal Refuge."),
                AyahItem(112, 3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "Lam yalid wa lam yoolad", "He neither begets nor is born,"),
                AyahItem(112, 4, "وَلَمْ يَكُنْ لَهُ كُفُوًا أَحَدٌ", "Wa lam yakul-lahu kufuwan ahad", "Nor is there to Him any equivalent.'")
            )
            113 -> listOf(
                AyahItem(113, 1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "Qul a'oodhu bi Rabbil-falaq", "Say, 'I seek refuge in the Lord of daybreak"),
                AyahItem(113, 2, "مِنْ شَرِّ مَا خَلَقَ", "Min sharri ma khalaq", "From the evil of that which He created"),
                AyahItem(113, 3, "وَمِنْ شَرِّ غَاسِقٍ إِذَا وَقَبَ", "Wa min sharri ghasiqin idha waqab", "And from the evil of darkness when it settles"),
                AyahItem(113, 4, "وَمِنْ شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "Wa min sharrin-naffathati fil-'uqad", "And from the evil of the blowers in knots"),
                AyahItem(113, 5, "وَمِنْ شَرِّ حَاسِدٍ إِذَا حَسَدَ", "Wa min sharri hasidin idha hasad", "And from the evil of an envier when he envies.'")
            )
            114 -> listOf(
                AyahItem(114, 1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "Qul a'oodhu bi Rabbin-nas", "Say, 'I seek refuge in the Lord of mankind,"),
                AyahItem(114, 2, "مَلِكِ النَّاسِ", "Malikin-nas", "The Sovereign of mankind."),
                AyahItem(114, 3, "إِلَٰهِ النَّاسِ", "Ilahin-nas", "The God of mankind,"),
                AyahItem(114, 4, "مِنْ شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "Min sharril-waswasil-khannas", "From the evil of the retreating whisperer -"),
                AyahItem(114, 5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "Alladhi yuwaswisu fee sudoorin-nas", "Who whispers [evil] into the breasts of mankind -"),
                AyahItem(114, 6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "Minal-jinnati wan-nas", "From among the jinn and mankind.'")
            )
            108 -> listOf(
                AyahItem(108, 1, "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ", "Inna a'taynaka al-kawthar", "Indeed, We have granted you, [O Muhammad], al-Kawthar."),
                AyahItem(108, 2, "فَصَلِّ لِرَبِّكَ وَانْحَرْ", "Fa salli li Rabbika wanhar", "So pray to your Lord and sacrifice [to Him alone]."),
                AyahItem(108, 3, "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ", "Inna shani'aka huwal-abtar", "Indeed, your enemy is the one cut off.")
            )
            103 -> listOf(
                AyahItem(103, 1, "وَالْعَصْرِ", "Wal-'asr", "By time,"),
                AyahItem(103, 2, "إِنَّ الْإِنْسَانَ لَفِي خُسْرٍ", "Innal-insana lafee khusr", "Indeed, mankind is in loss,"),
                AyahItem(103, 3, "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ", "Illal-ladheena amanoo wa 'amilus-salihati wa tawasaw bil-haqqi wa tawasaw bis-sabr", "Except for those who have believed and done righteous deeds and advised each other to truth and advised each other to patience.")
            )
            67 -> listOf(
                AyahItem(67, 1, "تَبَارَكَ الَّذِي بِيَدِهِ الْمُلْكُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ", "Tabarakal-ladhee biyadihil-mulku wa Huwa 'ala kulli shay'in qadeer", "Blessed is He in whose hand is dominion, and He is over all things competent -"),
                AyahItem(67, 2, "الَّذِي خَلَقَ الْمَوْتَ وَالْحَيَاةَ لِيَبْلُوَكُمْ أَيُّكُمْ أَحْسَنُ عَمَلًا ۚ وَهُوَ الْعَزِيزُ الْغَفُورُ", "Alladhee khalaqal-mawta wal-hayata liyabluwakum ayyukum ahsanu 'amala, wa Huwal-'Azeezul-Ghafoor", "[He] who created death and life to test you [as to] which of you is best in deed - and He is the Exalted in Might, the Forgiving -"),
                AyahItem(67, 3, "الَّذِي خَلَقَ سَبْعَ سَمَاوَاتٍ طِبَاقًا ۖ مَّا تَرَىٰ فِي خَلْقِ الرَّحْمَٰنِ مِن تَفَاوُتٍ", "Alladhee khalaqa sab'a samawatin tibaqan ma tara fee khalqir-Rahmani min tafawut", "[And] who created seven heavens in layers. You see not in the creation of the Most Merciful any inconsistency.")
            )
            else -> {
                // Generates initial Ayahs for the Surah
                val surah = SURAHS.find { it.number == surahNumber }
                listOf(
                    AyahItem(
                        surahNumber,
                        1,
                        "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                        "Bismillahir-Rahmanir-Raheem",
                        "In the name of Allah, the Entirely Merciful, the Especially Merciful."
                    ),
                    AyahItem(
                        surahNumber,
                        2,
                        "الْحَمْدُ لِلَّهِ الَّذِي أَنزَلَ عَلَىٰ عَبْدِهِ الْكِتَابَ وَلَمْ يَجْعَل لَّهُ عِوَجًا",
                        "Alhamdu lillahil-ladhee anzala 'ala 'abdihil-kitaba wa lam yaj'al lahu 'iwaja",
                        "[All] praise is [due] to Allah, who has sent down upon His Servant the Book and has not made therein any deviance."
                    ),
                    AyahItem(
                        surahNumber,
                        3,
                        "قَيِّمًا لِّيُنذِرَ بَأْسًا شَدِيدًا مِّن لَّدُنْهُ وَيُبَشِّرَ الْمُؤْمِنِينَ الَّذِينَ يَعْمَلُونَ الصَّالِحَاتِ أَنَّ لَهُمْ أَجْرًا حَسَنًا",
                        "Qayyiman liyundhira ba'san shadeedan min ladunhu wa yubash-shiral mu'mineenal-ladheena ya'maloonas-salihati anna lahum ajran hasana",
                        "[He has made it] straight, to warn of severe punishment from Him and to give good tidings to the believers who do righteous deeds that they will have a good reward."
                    )
                )
            }
        }
    }

    val DAILY_REMINDERS = listOf(
        "\"Indeed, prayer has been decreed upon the believers a decree of specified times.\" (Surah An-Nisa 4:103)",
        "\"And establish prayer and give Zakat and bow with those who bow [in worship and obedience].\" (Surah Al-Baqarah 2:43)",
        "\"So remember Me; I will remember you. And be grateful to Me and do not deny Me.\" (Surah Al-Baqarah 2:152)",
        "\"Unquestionably, by the remembrance of Allah hearts are assured.\" (Surah Ar-Ra'd 13:28)",
        "The Prophet ﷺ said: 'The coolness of my eyes has been made in the prayer.' (Sunan an-Nasa'i 3940)",
        "\"Indeed, Allah is with those who are patient.\" (Surah Al-Baqarah 2:153)",
        "\"My mercy encompasses all things.\" (Surah Al-A'raf 7:156)"
    )
}
