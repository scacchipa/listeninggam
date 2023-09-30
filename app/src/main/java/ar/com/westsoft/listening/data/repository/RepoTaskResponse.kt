package ar.com.westsoft.listening.data.repository

sealed class RepoTaskResponse {
    class Completed(val gui: Long) : RepoTaskResponse()
    object Uncompleted : RepoTaskResponse()
}
