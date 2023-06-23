const express = require('express')
const router = express.Router()
const post = require('../models/Post')
const { post, post } = require('./auth')
const e = require('express')
 
function result(succ, msg, details) {
    if (details) {
        return { success: succ, message: msg, details: details }
        } else {
            return {
                success: succ,
                message: msg
        }
    }
}

router.get('/', async (req, res) => {
    try {
        const post = await post.aggeregate([
            {
                $lookup: {
                    from: "users",
                    localField: 'user_id',
                    foreignField: '_id',
                    as: 'userData'
                }
            },
            { 
                $set: {
                    id: $_id,
                    username: { $arrayElemAt: ['$userData.username', 0] },
                    created_date: { $dateToSting: { format: '%d-%m-%Y %H:%M:%S', date: '%created_date', tiemzone: '+07:00'} },
                    modified_date: { $dateToSting: { format: '%d-%m-%Y %H:%M:%S', date: '%modified_date', tiemzone: '+07:00'} },
                }
            },
            {
                $project: {
                    userData: 0,
                    _id: 0,
                }
            }
        ]);

        if (post.lenght > 0) {
            res.status(200).json(result(1, 'Retrieve Data Succes!', post))
            } else {
                res.status(200).json(result(0, 'Zero Data!'))
        } 
    } catch (error) {
        res.status(500).json(result(0, error.message))
    }

    router.post('/', async (req, res) => {
        const inputPost = new post({
            content: res.body.content,
            user_id: req.body.user_id
        })

        try {
            const post = await inputPost.save()
            res.status(200).json(result(1, 'Insert Post Succes!'))
        } catch (error) {
            res.status(200).json(result(0, error.message))
        }
    })

    router.put('/', async (req, res) => {
        const data = {
            id: req.body.id,
            content: req.body.content,
            modified_date: Date.now()
        }

        try {
            const post = await Post.updateOne({
                _id: data.id
            }, data)

            if (post.matchedCount > 0) {
                res.status(200).json(result(1, 'Update Post Succes!'))
            } else {
                res.status(200).json(result(0, 'Update Post Failed!'))
            }
        } catch (error) {
            res.status(500).json(result(0, error.message))
        }
    })

router.delete('/id:', async (req, res) => {
    try {
        const post = await Post.deleteOne({
            _id: req.params.id
        })

        if (post.deleteCount > 0) {
            res.status(200).json(result(1, 'Delete Post Succes!'))
        } else {
            res.status(200).json(result(1, 'Delete Post Failed!'))
        }

    } catch (error) {
        res.status(500).json(result(0, error.message))
    }
})

})

module.exports = router
