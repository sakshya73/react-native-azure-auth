import { NativeModules, Linking, Platform } from 'react-native'

export default class Agent {
    openWeb(url, ephemeralSession = false, closeOnLoad = false) {
        try {
            if (!NativeModules.AzureAuth) {
                return Promise.reject(
                    new Error(
                        'Missing NativeModule. Please make sure you run `react-native link react-native-azure-auth`'
                    )
                )
            }
            const regex = /redirect_uri=([^&]+)/
            const match = url.match(regex)
            const redirectUri = match ? decodeURIComponent(match[1]) : null
            return new Promise((resolve, reject) => {
                let eventURL
                const removeListener = () => {
                    //This is done to handle backward compatibility with RN <= 0.64 which doesn't return EmitterSubscription on addEventListener
                    if (eventURL === undefined) {
                        Linking.removeEventListener('url', urlHandler)
                    } else {
                        eventURL.remove()
                    }
                }
                const urlHandler = (event) => {
                    NativeModules.AzureAuth.hide()
                    removeListener()
                    resolve(event.url)
                }
                const params =
                    Platform.OS === 'ios'
                        ? [ephemeralSession, closeOnLoad]
                        : [closeOnLoad]
                eventURL = Linking.addEventListener('url', urlHandler)
                NativeModules.AzureAuth.showUrl(
                    url,
                    redirectUri,
                    ...params,
                    (error, redirectURL) => {
                        console.log('showUrl>>>>>>>>>>>>')
                        console.log('url>>>>', url, params, error, redirectURL)
                        console.log('params>>>>', params)
                        console.log('error>>>>', error)
                        console.log('redirectURL>>>>', redirectURL)

                        removeListener()
                        if (error) {
                            reject(error)
                        } else if (redirectURL) {
                            resolve(redirectURL)
                        } else if (closeOnLoad) {
                            resolve()
                        } else {
                            reject(new Error('Unknown WebAuth error'))
                        }
                    }
                )
            })
        } catch (err) {
            console.log('errr>>>', err)
        }
    }

    generateRequestParams() {
        if (!NativeModules.AzureAuth) {
            return Promise.reject(
                new Error(
                    'Missing NativeModule. Please make sure you run `react-native link react-native-azure-auth`'
                )
            )
        }
        /* eslint no-unused-vars:0 */
        return new Promise((resolve, reject) => {
            NativeModules.AzureAuth.oauthParameters((parameters) => {
                resolve(parameters)
            })
        })
    }
}
