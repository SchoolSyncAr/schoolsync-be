package ar.org.schoolsync.domain

interface UserStatus

class NormalStatus : UserStatus

class AdminStatus : UserStatus

class ParentStatus: UserStatus
