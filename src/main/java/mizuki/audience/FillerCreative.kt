package mizuki.audience

class FillerCreative() :CreativeInterface {
    override val Id:Long
    override val enableSyncRequest:Boolean
    override val enablePassback:Boolean
    override val script:String
    override val height:Int
    override val width:Int

    override fun get(): CreativeInterface {

    }

}
