package mizuki.audience

class Creative() :CreativeInterface {
    override fun get(): CreativeInterface {
        throw UnsupportedOperationException("not implemented")
        //To change body of created functions use File | Settings | File Templates.
    }

    override val enableSyncRequest:Boolean = false
    override val enablePassback:Boolean = false
    override val script:String = ""
    override val height:Int = 0
    override val width:Int = 0
    override val Id:Long = 1L

}
