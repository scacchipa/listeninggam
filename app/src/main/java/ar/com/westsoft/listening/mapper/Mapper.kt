package ar.com.westsoft.listening.mapper

interface Mapper<E,D> {
    fun toDataSource(origin: E): D
    fun toEngine(origin: D): E
}