package mizuki.audience

interface CreativeInterface {
    val enableSyncRequest:Boolean
    val enablePassback:Boolean
    val script:String
    val height:Int
    val Widh:Int
    val Id:Int

    fun get(): CreativeInterface

}
