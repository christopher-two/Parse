package org.christophertwo.parse.feature.book.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.christophertwo.parse.domain.models.book.Book
import org.christophertwo.parse.domain.models.chapter.Chapter

class BookViewModel(
    private val id: String
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(BookState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                _state.update {
                    it.copy(
                        book = Book(
                            id = id,
                            title = "El Principito",
                            author = "Antoine de Saint-Exupéry",
                            year = 1943,
                            pages = 96,
                            image = null,
                            chapters = listOf(
                                Chapter(
                                    title = "Capítulo 1",
                                    content = """
                                        Cuando yo tenía seis años vi una vez un magnífico dibujo en un libro sobre el Bosque Virgen que se llamaba Historias vividas. Representaba una serpiente boa que se tragaba una fiera.

                                        En el libro se afirmaba: «Las serpientes boas se tragan su presa entera, sin masticarla. Después ya no pueden moverse y duermen durante los seis meses que dura su digestión».

                                        Medité mucho en ese momento sobre las aventuras de la jungla y a mi vez logré trazar mi primer dibujo, con un lápiz de color.

                                        Enseñé mi obra maestra a las personas mayores y les pregunté si mi dibujo les daba miedo.

                                        Me respondieron: «¿Por qué un sombrero iba a darnos miedo?»

                                        Mi dibujo no representaba un sombrero. Representaba una serpiente boa que digería un elefante. Dibujé entonces el interior de la serpiente boa a fin de que las personas mayores pudieran comprender. Siempre tienen necesidad de explicaciones.

                                        Las personas mayores me aconsejaron dejar de lado los dibujos de serpientes boas, ya fueran abiertas o cerradas, y concentrarme más bien en la geografía, la historia, el cálculo y la gramática. Así fue como abandoné, a la edad de seis años, una magnífica carrera de pintor. Había sido desanimado por el fracaso de mi dibujo número 1 y de mi dibujo número 2. Las personas mayores nunca comprenden nada por sí solas y es agotador para los niños tener que darles siempre explicaciones.

                                        Tuve que elegir, pues, otro oficio y aprendí a pilotar aviones. Volé un poco por todas partes en el mundo y la geografía, es cierto, me sirvió mucho. Sabía distinguir a primera vista China de Arizona. Es muy útil si uno se extravía durante la noche.

                                        Así tuve, a lo largo de mi vida, muchísimimos contactos con muchísimas personas serias. Viví mucho tiempo entre las personas mayores. Las observé muy de cerca. Eso no mejoró mucho mi opinión.

                                        Cuando encontraba a alguien que me parecía un poco lúcido, hacía con ella la experiencia de mi dibujo número 1, que siempre conservaba. Quería saber si era verdaderamente comprensiva. Pero la persona siempre me respondía: «Es un sombrero». Entonces no le hablaba ni de serpientes boas, ni de bosques vírgenes, ni de estrellas. Me ponía a su altura. Le hablaba de bridge, de golf, de política y de corbatas. Y la persona mayor se quedaba muy contenta de haber conocido a un hombre tan sensato.
                                    """.trimIndent()
                                ),
                                Chapter(
                                    title = "Capítulo 2",
                                    content = """
                                        Viví así, solo, sin nadie con quien hablar verdaderamente, hasta que tuve una avería en el desierto del Sahara, hace seis años. Algo se había roto en mi motor. Y como no tenía conmigo ni mecánico ni pasajeros, me dispuse a realizar, solo, una reparación difícil. Era para mí una cuestión de vida o muerte. Apenas tenía agua para beber para ocho días.

                                        La primera noche me dormí sobre la arena a mil millas de toda tierra habitada. Estaba más aislado que un náufrago en una balsa en medio del océano. Imaginen, pues, mi sorpresa cuando, al amanecer, me despertó una extraña vocecita que decía:

                                        —Por favor… ¡dibújame un cordero!
                                        —¿Eh?
                                        —¡Dibújame un cordero!

                                        Me puse de pie de un salto como si me hubiera golpeado un rayo. Me froté bien los ojos. Miré bien. Y vi a un hombrecito extraordinario que me examinaba gravemente.

                                        He aquí el mejor retrato que, más tarde, logré hacer de él. Pero mi dibujo, ciertamente, es mucho menos encantador que el modelo. No es culpa mía. Había sido desanimado en mi carrera de pintor por las personas mayores, a la edad de seis años, y no había aprendido a dibujar nada, salvo las boas cerradas y las boas abiertas.

                                        Miré, pues, esta aparición con los ojos redondos de asombro. No olviden que me encontraba a mil millas de toda región habitada. Ahora bien, mi hombrecito no me parecía ni perdido, ni muerto de cansancio, ni muerto de hambre, ni muerto de sed, ni muerto de miedo. No tenía en absoluto la apariencia de un niño perdido en medio del desierto, a mil millas de toda región habitada. Cuando por fin logré hablar, le dije:

                                        —Pero… ¿qué haces tú aquí?

                                        Y él me repitió entonces, muy suavemente, como una cosa muy seria:

                                        —Por favor… dibújame un cordero…

                                        Cuando el misterio es demasiado impresionante, es imposible desobedecer. Por absurdo que me pareciera a mil millas de todos los lugares habitados y en peligro de muerte, saqué de mi bolsillo una hoja de papel y una pluma estilográfica. Pero entonces recordé que yo había estudiado sobre todo la geografía, la historia, el cálculo y la gramática y le dije al hombrecito (con un poco de mal humor) que no sabía dibujar. Me respondió:

                                        —No importa. Dibújame un cordero.

                                        Como nunca había dibujado un cordero, rehice para él uno de los dos únicos dibujos de los que era capaz: el de la boa cerrada. Y quedé estupefacto al oír al hombrecito responderme:

                                        —¡No, no! ¡No quiero un elefante en una boa! Una boa es muy peligrosa y un elefante es muy voluminoso. En mi casa todo es muy pequeño. Necesito un cordero. Dibújame un cordero.

                                        Entonces dibujé.

                                        Él miró atentamente, luego:

                                        —¡No! Éste ya está muy enfermo. Haz otro.

                                        Dibujé:

                                        Mi amigo sonrió amablemente, con indulgencia:

                                        —Ves… no es un cordero, es un carnero. Tiene cuernos…

                                        Rehice de nuevo mi dibujo:

                                        Pero fue rechazado como los anteriores:

                                        —Éste es demasiado viejo. Quiero un cordero que viva mucho tiempo.

                                        Entonces, falto de paciencia, como tenía prisa por empezar a desmontar mi motor, garabateé este dibujo.

                                        Y le lancé:

                                        —Ésta es la caja. El cordero que quieres está dentro.

                                        Pero quedé muy sorprendido al ver iluminarse el rostro de mi joven juez:

                                        —¡Es exactamente como yo lo quería! ¿Crees que este cordero necesite mucha hierba?
                                        —¿Por qué?
                                        —Porque en mi casa todo es muy pequeño…
                                        —Seguramente será suficiente. Te he dado un cordero muy pequeño.

                                        Inclinó la cabeza sobre el dibujo:

                                        —No tan pequeño como eso… ¡Mira! Se ha dormido…

                                        Y así fue como conocí al principito.
                                    """.trimIndent()
                                )
                            )
                        )
                    )
                }
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = BookState()
        )

    fun onAction(action: BookAction) {
        when (action) {
            BookAction.Add -> {

            }

            BookAction.Edit -> {

            }

            BookAction.NavigateToNextChapter -> {
                _state.update {
                    val nextIndex = it.currentChapterIndex + 1
                    if (nextIndex < (it.book?.chapters?.size ?: 0)) {
                        it.copy(currentChapterIndex = nextIndex)
                    } else {
                        it
                    }
                }
            }
        }
    }

}