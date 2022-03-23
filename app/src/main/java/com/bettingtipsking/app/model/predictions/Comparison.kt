package com.bettingtipsking.app.model.predictions

data class Comparison(
    val att: Att,
    val def: Def,
    val form: Form,
    val goals: Goals,
    val h2h: H2h,
    val poisson_distribution: PoissonDistribution,
    val total: Total
)