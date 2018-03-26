package bui365.mobile.main.model.pojo


class FacebookPOJO {

    var likes: Likes = Likes()
    var share: Share = Share()
    var id: String? = null

    override fun toString(): String {
        return "FacebookPOJO(likes=$likes,share=$share)"
    }

    inner class Share {
        var commentCount: Int = 0
        var shareCount: Int = 0
        override fun toString(): String {
            return "Share(commentCount=$commentCount, shareCount=$shareCount)"
        }
    }

    inner class Likes {
        var summary: Summary = Summary()
        var id: String? = null

        inner class Summary {
            var likeCount: Int = 0
            var canLike: Boolean = false
            var hasLiked: Boolean = false
        }

        override fun toString(): String {
            return "Likes(summary=${summary.likeCount})"
        }
    }
}