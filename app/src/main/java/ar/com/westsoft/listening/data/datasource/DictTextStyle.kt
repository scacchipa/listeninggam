package ar.com.westsoft.listening.data.datasource

sealed class DictTextStyle {
    data object Paragraph: DictTextStyle()
    data object Head1: DictTextStyle()
    data object Head2: DictTextStyle()
    data object Head3: DictTextStyle()
    data object Head4: DictTextStyle()
    data object Head5: DictTextStyle()
    data object Head6: DictTextStyle()
}