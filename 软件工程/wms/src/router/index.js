import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/HomeView.vue'
import backedmanageView from '../views/backedmanage.vue'
import user from "@/components/user";
import booksview from "@/views/booksview";
import book from "@/components/book";
import ident from "@/components/ident";
import myshopping from "@/components/myshopping";
import myident from "@/components/myident";
import vuebookbykind from "@/components/vuebookbykind";
import test from "@/views/test";

Vue.use(VueRouter)

const routes = [
    {
        path: '/test',
        name: 'test',
        component: test,
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
        redirect: '/home/booksview',
        children: [
            {
                path: '/backedmanage',
                name: 'backedmanage',
                component: backedmanageView,
                redirect: '/backedmanage/users',
                children: [
                    {
                        path: '/backedmanage/users',
                        name: 'user',
                        component: user,

                    },
                    {
                        path: '/backedmanage/books',
                        name: 'book',
                        component: book,

                    },
                    {
                        path: '/backedmanage/idents',
                        name: 'ident',
                        component: ident,

                    }
                ]
            },
            {
                path: '/home/booksview',
                name: 'booksview',
                component: booksview,
            },
            {
                path: '/home/myshopping',
                name: 'myshopping',
                component: myshopping,
            },
            {
                path: '/home/myident',
                name: 'myident',
                component: myident,
            },
            {
                path: '/vuebookbykind/:type',
                name: 'vuebookbykind',
                component: vuebookbykind,
            }
        ]
    },
]

const router = new VueRouter({
    // mode: 'history',
    routes
})

export default router
