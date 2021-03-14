package pdp.uz.a8_4androidlessons.models

class Info2 {
    var id: Int? = null
    var info: ArrayList<Info>? = null

    constructor(id: Int?, info: ArrayList<Info>?) {
        this.id = id
        this.info = info
    }

    constructor(id: Int?) {
        this.id = id
    }

}