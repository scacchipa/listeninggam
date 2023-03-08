package ar.com.westsoft.listening.data.engine

sealed class RepoTaskResponse {
    class Completed(val gui: Long) : RepoTaskResponse()
    object Uncompleted : RepoTaskResponse()
}
